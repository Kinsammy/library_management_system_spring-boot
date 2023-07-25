package com.sametech.library_management_system.service.serviceImplementation;

import com.sametech.library_management_system.data.dto.request.BookRequest;
import com.sametech.library_management_system.data.models.entity.Author;
import com.sametech.library_management_system.data.models.entity.Book;
import com.sametech.library_management_system.data.models.entity.Genre;
import com.sametech.library_management_system.service.serviceInterface.IBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {
    @Autowired
    private IBookService bookService;

    private BookRequest bookRequest;

    @BeforeEach
    void setUp(){
        bookRequest = BookRequest.builder()
                .title("Spring boot in action")
                .isbn("isbn-17")
                .description("This book is base on Sprint security")
                .genre(Genre.FINANCIAL)
                .build();


    }

    @Test
    void addNewBook() {
        var response = bookService.addNewBook(bookRequest);
        assertThat(response).isNotNull();

    }

    @Test
    void getBookById() {
    }

    @Test
    void saveBook() {
    }

    @Test
    void getAllBooksPerPage() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void updateBook() {
    }
}