package com.sametech.library_management_system.controller;

import com.sametech.library_management_system.data.dto.request.BookRequest;
import com.sametech.library_management_system.data.dto.response.BookResponse;
import com.sametech.library_management_system.data.models.entity.Book;
import com.sametech.library_management_system.service.serviceInterface.IBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@AllArgsConstructor
public class BookController {
    private final IBookService bookService;

    @PostMapping("/add")
    public ResponseEntity<BookResponse> addNewBook(@RequestBody BookRequest request){
        var response = bookService.addNewBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Long bookId){
        var foundBook = bookService.getBookById(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(foundBook);
    }

    @GetMapping("/all/{pageNumber}")
    public ResponseEntity<?> getAllBooks(@PathVariable int pageNumber){
        var response = bookService.getAllBooksPerPage(pageNumber);
        return ResponseEntity.ok(response.getContent());
    }

    @PutMapping("/book")
    public ResponseEntity<?> updateBook(@RequestBody Book book){
        var response = bookService.updateBook(book);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @DeleteMapping("{bookId}")
    public void deleteBook(@PathVariable("bookId") Long bookId){
        bookService.deleteBook(bookId);
    }


    @GetMapping("/search/title")
    public ResponseEntity<List<Book>> searchBookByTitle(@RequestParam("title") String title){
        List<Book> books = bookService.geBooksByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/search/author")
    public ResponseEntity<List<Book>> searchBookByAuthor(@RequestParam("author") String author){
        List<Book> books = bookService.geBooksByTitle(author);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }
}
