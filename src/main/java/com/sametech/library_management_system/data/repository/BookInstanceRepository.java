package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.entity.Book;
import com.sametech.library_management_system.data.models.entity.BookInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookInstanceRepository extends JpaRepository<BookInstance, Long> {
    List<BookInstance> findAvailableBookInstancesByBook(Book book);
}
