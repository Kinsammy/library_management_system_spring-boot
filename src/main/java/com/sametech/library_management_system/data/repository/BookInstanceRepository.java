package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.entity.Book;
import com.sametech.library_management_system.data.models.entity.BookInstance;
import com.sametech.library_management_system.data.models.entity.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookInstanceRepository extends JpaRepository<BookInstance, Long> {

    List<BookInstance> findAvailableBookInstancesByBook(Book book);
    List<BookInstance> findByBookStatus(BookStatus bookStatus);
}
