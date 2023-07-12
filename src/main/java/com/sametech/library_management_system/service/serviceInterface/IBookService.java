package com.sametech.library_management_system.service.serviceInterface;

import com.github.fge.jsonpatch.JsonPatch;
import com.sametech.library_management_system.data.dto.request.BookRequest;
import com.sametech.library_management_system.data.dto.response.BookResponse;
import com.sametech.library_management_system.data.models.entity.Book;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IBookService {
    BookResponse addNewBook(BookRequest bookRequest);
    Book getBookById(Long bookId);
    void saveBook(Book book);
    Optional<Book> getBookBy(Long bookId);
    Book updateBook(Long bookId, JsonPatch updatePayLoad);

    Page<Book> getAllBooksPerPage(int pageNumber);
    BookResponse deleteBook(Long  bookId);
    BookResponse updateBook(BookRequest bookRequest);
}
