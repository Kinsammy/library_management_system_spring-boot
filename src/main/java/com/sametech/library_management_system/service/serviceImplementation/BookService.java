package com.sametech.library_management_system.service.serviceImplementation;


import com.sametech.library_management_system.data.dto.request.BookRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.BookResponse;
import com.sametech.library_management_system.data.models.entity.Book;
import com.sametech.library_management_system.data.models.entity.BookInstance;
import com.sametech.library_management_system.data.models.entity.BookStatus;
import com.sametech.library_management_system.data.repository.BookInstanceRepository;
import com.sametech.library_management_system.data.repository.BookRepository;
import com.sametech.library_management_system.exception.BookNotFoundException;
import com.sametech.library_management_system.exception.LibraryLogicException;
import com.sametech.library_management_system.service.serviceInterface.IBookService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sametech.library_management_system.util.AppUtilities.NUMBER_OF_INSTANCES_PER_BOOK;
import static com.sametech.library_management_system.util.AppUtilities.NUMBER_OF_ITEMS_PER_PAGE;

@Service
@AllArgsConstructor
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private final BookInstanceRepository bookInstanceRepository;
    @Override
    public BookResponse addNewBook(BookRequest bookRequest) {
        var book = Book.builder()
                .id(bookRequest.getId())
                .title(bookRequest.getTitle())
                .isbn(bookRequest.getIsbn())
                .description(bookRequest.getDescription())
                .genre(bookRequest.getGenre())
                .dateAdded(LocalDateTime.now().toString())
                .author(bookRequest.getAuthor())
                .build();
        Book savedBook = saveBook(book);
        List<BookInstance> bookInstances = new ArrayList<>();
        for (int instance = 0; instance < NUMBER_OF_INSTANCES_PER_BOOK; instance++){
            bookInstances.add(BookInstance.builder()
                    .book(savedBook)
                    .bookStatus(BookStatus.AVAILABLE)
                    .build());
        }
        bookInstanceRepository.saveAll(bookInstances);
        savedBook.setInstances(bookInstances);

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
    public Book saveBook(Book book) {
        bookRepository.save(book);
        return book;
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
    public List<Book> searchBooksByTitle(String title) {
        List<Book> books = bookRepository.findBookByTitleContainingIgnoreCase(title);

        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found with the given title.");
        }

        return books;
    }

    @Override
    public List<Book> searchBooksByAuthor(String authorName) {
        List<Book> books = bookRepository.findBookByAuthorContainingIgnoreCase(authorName);
        if (books.isEmpty()){
            throw new BookNotFoundException("No books found with the given author.");
        }
        return books;
    }
}
