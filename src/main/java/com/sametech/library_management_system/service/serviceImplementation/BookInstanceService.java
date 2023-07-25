package com.sametech.library_management_system.service.serviceImplementation;

import com.sametech.library_management_system.data.models.entity.Book;
import com.sametech.library_management_system.data.models.entity.BookCopy;
import com.sametech.library_management_system.data.models.entity.BookInstance;
import com.sametech.library_management_system.data.models.entity.BookStatus;
import com.sametech.library_management_system.data.models.users.Librarian;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import com.sametech.library_management_system.data.repository.BookInstanceRepository;
import com.sametech.library_management_system.data.repository.BookRepository;
import com.sametech.library_management_system.data.repository.LibrarianRepository;
import com.sametech.library_management_system.data.repository.LibraryUserRepository;
import com.sametech.library_management_system.exception.BookNotFoundException;
import com.sametech.library_management_system.exception.LibraryLogicException;
import com.sametech.library_management_system.exception.UserNotFoundException;
import com.sametech.library_management_system.service.serviceInterface.IBookInstanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookInstanceService implements IBookInstanceService {
    private final LibraryUserRepository libraryUserRepository;
    private final BookInstanceRepository bookInstanceRepository;
    private final BookRepository bookRepository;
    private final LibrarianRepository librarianRepository;

//    @Override
//    public boolean borrowBook(Long libraryUserId, Long bookInstanceId, Long librarianId) {
//        return false;
//    }

    @Override
    public BookInstance createBorrowRequest(Book book, LibraryUser libraryUser, Librarian librarian) {
        if (book == null || libraryUser == null || librarian == null){
            throw new LibraryLogicException("Invalid input parameters.");
        }

        BookInstance borrowRequest = BookInstance.builder()
                .bookStatus(BookStatus.PENDING_APPROVAL)
                .books(Collections.singletonList(book))
                .borrowedBy(libraryUser)
                .approvedBy(librarian)
                .borrowedDate(LocalDateTime.now())
                .build();
        return bookInstanceRepository.save(borrowRequest);
    }

    @Override
    public BookInstance createReturnBookRequest(Book book, LibraryUser libraryUser, Librarian librarian) {
       if (book == null || libraryUser == null || librarian == null){
           throw new LibraryLogicException("Invalid input parameters.");
       }
        BookInstance returnRequest = BookInstance.builder()
                .bookStatus(BookStatus.PENDING_APPROVAL)
                .books(Collections.singletonList(book))
                .borrowedBy(libraryUser)
                .approvedBy(librarian)
                .borrowedDate(LocalDateTime.now())
                .build();
        return bookInstanceRepository.save(returnRequest);
    }

    @Override
    public BookInstance createBorrowBookRequest(Long libraryUserId, Long bookId, Long librarianId) {
        LibraryUser libraryUser = libraryUserRepository.findById(libraryUserId)
                .orElseThrow(()-> new UserNotFoundException("Library user not found."));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new BookNotFoundException("Book not found."));

        Librarian librarian = librarianRepository.findById(librarianId)
                .orElseThrow(() -> new UserNotFoundException("Librarian not found."));

        return createBorrowRequest(book, libraryUser, librarian);
    }


//    @Override
//    public boolean borrowBook(Long libraryUserId, Long bookInstanceId, Long librarianId) {
//        var libraryUser = libraryUserRepository.findById(libraryUserId).orElse(null);
//        if (libraryUser == null || !libraryUser.getUserDetails().isEnabled()){
//            return false;
//        }
//        var librarian = librarianRepository.findById(librarianId).orElse(null);
//        if (librarian == null || !librarian.getUserDetails().isEnabled()){
//            return false;
//        }
//        var bookInstance = bookInstanceRepository.findById(bookInstanceId).orElse(null);
//        if (bookInstance == null || bookInstance.getBookStatus() != BookStatus.AVAILABLE){
//            return false;
//        }
//
//        Book bookToBorrow = bookInstance.getBooks().get(0);
//        bookInstance.setBorrowedBy(libraryUser);
//        bookInstance.setBookStatus(BookStatus.BORROWED);
//        bookInstance.setBorrowedDate(LocalDateTime.now());
//        bookInstance.setApprovedBy(librarian);
//        bookInstanceRepository.save(bookInstance);
//        return true;
//    }
}
