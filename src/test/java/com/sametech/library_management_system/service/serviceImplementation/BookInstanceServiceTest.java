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
@SpringBootTest

class BookInstanceServiceTest {
    @InjectMocks
    private BookInstanceService bookInstanceService;
    @Mock
    private LibraryUserRepository libraryUserRepository;
    @Mock
    private LibrarianRepository librarianRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookInstanceRepository bookInstanceRepository;
    @BeforeEach
    void setUp() {
        Long libraryUserId = 1L;
        String title = "Silence, The (Das letzte Schweigen)";
        Long librarianId = 1L;

        var libraryUser = new LibraryUser();
        Mockito.when(libraryUserRepository.findById(libraryUserId))
                .thenReturn(Optional.of(libraryUser));

        var book = new Book();
        book.setTitle(title);
        Mockito.when(bookRepository.findBookByTitleContainingIgnoreCase(title)).thenReturn(List.of(book));
        var librarian = new Librarian();
        Mockito.when(librarianRepository.findById(librarianId))
                .thenReturn(Optional.of(librarian));

        var availableBookInstance = new BookInstance();
        availableBookInstance.setBookStatus(BookStatus.AVAILABLE);
        availableBookInstance.setBook(book);
        List<BookInstance> availableBookInstances = List.of(availableBookInstance);
        Mockito.when(bookInstanceRepository.findAvailableBookInstancesByBook(book)).thenReturn(availableBookInstances);
    }

    @Test
    void borrowBookRequestTest() {
        var response = bookInstanceService.borrowBookRequest(1L, "Silence, The (Das letzte Schweigen)");
        assertThat(response).isNotNull();
    }

    @Test
    void approveBorrowBookRequest() {
        var response = bookInstanceService.approveBorrowBookRequest(1L, 1L);
        assertThat(response).isNotNull();
    }

    @Test
    public void testIsBookAvailableWithNoAvailableInstances() {
        // Create a test book
        Book testBook = new Book();
        testBook.setTitle("Test Book");

        // Create an empty list of book instances
        List<BookInstance> noInstances = new ArrayList<>();

        // Mock the behavior of the bookInstanceRepository
        Mockito.when(bookInstanceRepository.findAvailableBookInstancesByBook(testBook)).thenReturn(noInstances);

        // Call the isBookAvailable method
        boolean result = bookInstanceService.isBookAvailable(testBook);

        // Assert that the result should be false since there are no available instances
        assertFalse(result);
    }


    @Test
    void returnBookRequest() {
    }

    @Test
    void approveReturnBookRequest() {
    }
}