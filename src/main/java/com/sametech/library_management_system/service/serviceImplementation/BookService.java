package com.sametech.library_management_system.service.serviceImplementation;


import com.sametech.library_management_system.data.dto.request.BookRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.BookResponse;
import com.sametech.library_management_system.data.models.entity.Author;
import com.sametech.library_management_system.data.models.entity.Book;
import com.sametech.library_management_system.data.repository.BookRepository;
import com.sametech.library_management_system.exception.LibraryLogicException;
import com.sametech.library_management_system.service.serviceInterface.IBookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.sametech.library_management_system.util.AppUtilities.NUMBER_OF_ITEMS_PER_PAGE;

@Service
@AllArgsConstructor
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    @Override
    public BookResponse addNewBook(BookRequest bookRequest) {
        var book = Book.builder()
                .title(bookRequest.getTitle())
                .isbn(bookRequest.getIsbn())
                .description(bookRequest.getDescription())
                .genre(bookRequest.getGenre())
                .dateAdded(LocalDateTime.now().toString())
                .author(bookRequest.getAuthor())
                .build();
        return BookResponse.builder()
                .id(book.getId())
                .message("Book created successfully")
                .build();
    }

    @Override
    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public Page<Book> getAllBooksPerPage(int pageNumber) {
        if (pageNumber < 1) pageNumber = 0;
        else pageNumber = pageNumber - 1;
        Pageable pageable = PageRequest.of(pageNumber, NUMBER_OF_ITEMS_PER_PAGE);
        return bookRepository.findAll(pageable);
    }

    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    public ApiResponse updateBook(Book book) {
        bookRepository.findById(book.getId()).orElseThrow(()->
                new LibraryLogicException(
                        String.format("Book with id %d does not exist", book.getId())
                ));

        bookRepository.save(book);
        return ApiResponse.builder()
                .message("Book successfully updated")
                .build();
    }

    @Override
    public List<Book> geBooksByTitle(String title) {
        return bookRepository.findBookByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Book> geBooksByAuthor(String authorName) {
        return bookRepository.findByAuthorNameContainingIgnoreCase(authorName);
    }
}
