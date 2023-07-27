package com.sametech.library_management_system.service.serviceImplementation;

import com.sametech.library_management_system.data.models.entity.Book;
import com.sametech.library_management_system.data.models.entity.BookInstance;
import com.sametech.library_management_system.data.models.entity.BookStatus;
import com.sametech.library_management_system.data.models.users.Librarian;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import com.sametech.library_management_system.data.repository.BookInstanceRepository;
import com.sametech.library_management_system.data.repository.BookRepository;
import com.sametech.library_management_system.data.repository.LibrarianRepository;
import com.sametech.library_management_system.data.repository.LibraryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
class BookInstanceServiceTest {

    @Autowired
    private BookInstanceService bookInstanceService;
    @Mock
    private LibraryUserRepository libraryUserRepository;
    @Mock
    private LibrarianRepository librarianRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookInstanceRepository bookInstanceRepository;
    Long libraryUserId = 1L;
    String title = "Deitel";
    Long librarianId = 1L;
    Long bookInstanceId = 1L;

    @BeforeEach
    void setUp() {
        var libraryUser = new LibraryUser();
        Mockito.when(libraryUserRepository.findById(libraryUserId))
                .thenReturn(Optional.of(libraryUser));

        var book = new Book();
        book.setTitle(title);
        Mockito.when(bookRepository.findBookByTitleContainingIgnoreCase(title)).thenReturn(List.of(book));
        var bookInstance = new BookInstance();
        bookInstance.setBookStatus(BookStatus.AVAILABLE);
        Mockito.when(bookInstanceRepository.findAvailableBookInstancesByBook(book)).thenReturn(List.of(bookInstance));
        var librarian = new Librarian();
        Mockito.when(librarianRepository.findById(librarianId))
                .thenReturn(Optional.of(librarian));


    }

    @Test
    void borrowBookRequestTest() {
        var response = bookInstanceService.borrowBookRequest(libraryUserId, title);
        assertThat(response).isNotNull();
    }

    @Test
    void approveBorrowBookRequest() {
        var response = bookInstanceService.approveBorrowBookRequest(bookInstanceId, librarianId);
        assertThat(response).isNotNull();
    }



    @Test
    void returnBookRequest() {
        var response = bookInstanceService.returnBookRequest(libraryUserId, bookInstanceId);
        assertThat(response).isNotNull();
    }

    @Test
    void approveReturnBookRequest() {
        var response = bookInstanceService.approveReturnBookRequest(bookInstanceId, librarianId);
        assertThat(response).isNotNull();
    }
}