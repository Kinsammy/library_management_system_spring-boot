package com.sametech.library_management_system.service.serviceImplementation;

import com.sametech.library_management_system.data.models.entity.Book;
import com.sametech.library_management_system.data.models.entity.BookInstance;
import com.sametech.library_management_system.data.models.entity.BookStatus;
import com.sametech.library_management_system.data.repository.BookInstanceRepository;
import com.sametech.library_management_system.data.repository.BookRepository;
import com.sametech.library_management_system.data.repository.LibrarianRepository;
import com.sametech.library_management_system.data.repository.LibraryUserRepository;
import com.sametech.library_management_system.exception.BookNotFoundException;
import com.sametech.library_management_system.exception.UnauthorizedBookReturnException;
import com.sametech.library_management_system.exception.UserNotFoundException;
import com.sametech.library_management_system.service.serviceInterface.IBookInstanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookInstanceService implements IBookInstanceService {
    private final LibraryUserRepository libraryUserRepository;
    private final BookInstanceRepository bookInstanceRepository;
    private final BookRepository bookRepository;
    private final LibrarianRepository librarianRepository;


    @Override
    public void borrowBookRequest(Long libraryUserId, String searchQuery) {
        var libraryUser = libraryUserRepository.findById(libraryUserId).orElseThrow(
                ()-> new UserNotFoundException("Library User not found"));
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(searchQuery, searchQuery);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found with the given title or author.");
        }
        Book bookToBorrow = books.get(0);

       List<BookInstance> availableBookInstances = bookInstanceRepository.findAvailableBookInstancesByBook(bookToBorrow);
       if (availableBookInstances.isEmpty()) {
           throw new BookNotFoundException("Book is not available for borrowing.");
       }
       BookInstance bookInstanceToBorrow = availableBookInstances.get(0);
       bookInstanceToBorrow.setBookStatus(BookStatus.BORROW_REQUEST);
       bookInstanceToBorrow.setBorrowedBy(libraryUser);
       bookInstanceToBorrow.setApprovedBy(null);
       bookInstanceToBorrow.setBorrowedDate(LocalDateTime.now());
       bookInstanceRepository.save(bookInstanceToBorrow);
    }

    @Override
    public void approveBorrowBookRequest(Long bookInstanceId, Long librarianId) {
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
    }

    @Override
    public void returnBookRequest(Long libraryUserId, Long bookInstanceId) {
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

    }

    @Override
    public void approveReturnBookRequest(Long bookInstanceId, Long librarianId) {
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
    }


}
