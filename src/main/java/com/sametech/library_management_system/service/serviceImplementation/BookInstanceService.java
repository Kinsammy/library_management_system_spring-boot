package com.sametech.library_management_system.service.serviceImplementation;

import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.models.entity.Book;
import com.sametech.library_management_system.data.models.entity.BookInstance;
import com.sametech.library_management_system.data.models.entity.BookStatus;
import com.sametech.library_management_system.data.repository.BookInstanceRepository;
import com.sametech.library_management_system.data.repository.BookRepository;
import com.sametech.library_management_system.data.repository.LibrarianRepository;
import com.sametech.library_management_system.data.repository.LibraryUserRepository;
import com.sametech.library_management_system.exception.BookNotAvailableException;
import com.sametech.library_management_system.exception.BookNotFoundException;
import com.sametech.library_management_system.exception.UnauthorizedBookReturnException;
import com.sametech.library_management_system.exception.UserNotFoundException;
import com.sametech.library_management_system.service.serviceInterface.IBookInstanceService;
import com.sametech.library_management_system.service.serviceInterface.IBookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BookInstanceService implements IBookInstanceService {
    private final LibraryUserRepository libraryUserRepository;
    private final BookInstanceRepository bookInstanceRepository;
    private final BookRepository bookRepository;
    private final IBookService bookService;
    private final LibrarianRepository librarianRepository;


    @Override
    public ApiResponse borrowBookRequest(Long libraryUserId, String title) {
        var libraryUser = libraryUserRepository.findById(libraryUserId).orElseThrow(
                ()-> new UserNotFoundException("Library User not found"));
        List<Book> books = bookRepository.findBookByTitleContainingIgnoreCase(title);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found with the given title");
        }
        Book bookToBorrow = null;
        for (Book book : books) {
            System.out.printf("Checking book availability for: {%s}", book.getTitle());
            if (isBookAvailable(book)) {
                System.out.printf("%s is available for borrowing.", book.getTitle());
                bookToBorrow = book;
                break;
            }
        }

        if (bookToBorrow == null) {
            throw new BookNotFoundException("Book is not available for borrowing.");
        }


        List<BookInstance> availableBookInstances = bookInstanceRepository.findAvailableBookInstancesByBook(bookToBorrow);
        System.out.printf("Found {%d} available instances for %s: {}", availableBookInstances.size(), bookToBorrow.getTitle());


        BookInstance bookInstanceToBorrow = availableBookInstances.get(0);
        bookInstanceToBorrow.setBook(bookToBorrow);
        bookInstanceToBorrow.setBookStatus(BookStatus.BORROW_REQUEST);
        bookInstanceToBorrow.setBorrowedBy(libraryUser);
        bookInstanceToBorrow.setApprovedBy(null);
        bookInstanceToBorrow.setBorrowedDate(LocalDateTime.now());
        bookInstanceRepository.save(bookInstanceToBorrow);
        return ApiResponse.builder()
                .message("Book borrow request is sent meet Librarian to approve the request.")
                .build();

    }

    public boolean isBookAvailable(Book book) {
        List<BookInstance> availableInstances = bookInstanceRepository.findByBookStatus(BookStatus.AVAILABLE);
        boolean isAvailable = availableInstances.stream().anyMatch(instance -> instance.getBook().equals(book));
        System.out.printf("%s is available for borrowing: %s%n", book.getTitle(), isAvailable);
        return isAvailable;
    }


    @Override
    public List<BookInstance> getAvailableBookInstancesByBook(Book book) {
        List<BookInstance> bookInstances = bookInstanceRepository.findAvailableBookInstancesByBook(book);

        if (bookInstances.isEmpty()){
            throw new BookNotFoundException("No book instance found");
        }
        return bookInstances;
    }

    @Override
    public ApiResponse approveBorrowBookRequest(Long bookInstanceId, Long librarianId) {
        var librarian = librarianRepository.findById(librarianId)
                .orElseThrow(()-> new UserNotFoundException("Librarian not found."));
        var bookInstance = bookInstanceRepository.findById(bookInstanceId)
                .orElseThrow(()-> new BookNotFoundException("BookInstance bot found."));
        if (bookInstance.getBookStatus() != BookStatus.BORROW_REQUEST){
            throw new BookNotFoundException("Book is not currently requested for borrow.");
        }
        bookInstance.setBookStatus(BookStatus.BORROWED);
        bookInstance.setApprovedBy(librarian);
        bookInstance.setApprovedDate(LocalDateTime.now());
        bookInstanceRepository.save(bookInstance);
        return ApiResponse.builder()
                .message(String.format("Book borrow request is approved by %s", librarianId))
                .build();
    }

    @Override
    public ApiResponse returnBookRequest(Long libraryUserId, Long bookInstanceId) {
        var libraryUser = libraryUserRepository.findById(libraryUserId)
                .orElseThrow(()-> new UserNotFoundException("Library user not found."));
        var bookInstance = bookInstanceRepository.findById(bookInstanceId)
                .orElseThrow(()-> new BookNotFoundException("Book Instance not found"));
        if (bookInstance.getBookStatus() != BookStatus.BORROWED){
            throw new BookNotFoundException("Book is not currently borrowed.");
        }
        if (!bookInstance.getBorrowedBy().equals(libraryUser)){
            throw new UnauthorizedBookReturnException(
                    String.format("This book was not borrowed by %s", libraryUser)
            );
        }

        bookInstance.setBookStatus(BookStatus.RETURN_REQUEST);
        bookInstance.setBorrowedBy(null);
        bookInstance.setReturnedDate(LocalDateTime.now());
        bookInstance.setApprovedBy(null);
        bookInstanceRepository.save(bookInstance);
        return ApiResponse.builder()
                .message("Book return request is sent meet Librarian to approve the request.")
                .build();
    }

    @Override
    public ApiResponse approveReturnBookRequest(Long bookInstanceId, Long librarianId) {
        var librarian = librarianRepository.findById(librarianId)
                .orElseThrow(()-> new UserNotFoundException("Librarian not found."));
        var bookInstance = bookInstanceRepository.findById(bookInstanceId)
                .orElseThrow(()-> new BookNotFoundException("BookInstance bot found."));

        if (bookInstance.getBookStatus() != BookStatus.RETURN_REQUEST){
            throw new BookNotFoundException("Book is not currently requested for return.");
        }
        bookInstance.setBookStatus(BookStatus.RETURNED);
        bookInstance.setApprovedBy(librarian);
        bookInstance.setApprovedDate(LocalDateTime.now());
        bookInstance.setBookStatus(BookStatus.AVAILABLE);
        bookInstanceRepository.save(bookInstance);

        return ApiResponse.builder()
                .message(String.format("Book return request is approved by %s", librarianId))
                .build();
    }


}
