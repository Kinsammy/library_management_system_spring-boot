package com.sametech.library_management_system.service.serviceInterface;

import com.sametech.library_management_system.data.dto.request.BookRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.BookResponse;
import com.sametech.library_management_system.data.models.entity.Book;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IBookService {
    BookResponse addNewBook(Book book);
    Optional<Book> getBookById(Long bookId);
    void saveBook(Book book);
    Page<Book> getAllBooksPerPage(int pageNumber);
    void deleteBook(Long  bookId);
    ApiResponse updateBook(Book book);
}
