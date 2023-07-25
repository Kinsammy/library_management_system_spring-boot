package com.sametech.library_management_system.service.serviceInterface;

import com.sametech.library_management_system.data.models.entity.Book;
import com.sametech.library_management_system.data.models.entity.BookCopy;
import com.sametech.library_management_system.data.models.entity.BookInstance;
import com.sametech.library_management_system.data.models.users.Librarian;
import com.sametech.library_management_system.data.models.users.LibraryUser;

import java.util.List;

public interface IBookInstanceService {
//    boolean borrowBook(Long libraryUserId, Long bookInstanceId, Long librarianId);
    BookInstance createBorrowRequest(Book book, LibraryUser libraryUser, Librarian librarian);
    BookInstance createReturnBookRequest(Book book, LibraryUser libraryUser, Librarian librarian);

    BookInstance createBorrowBookRequest(Long libraryUserId, Long bookId, Long librarianId);

}
