package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBookByTitleContainingIgnoreCase(String title);

    List<Book> findBookByAuthorContainingIgnoreCase(String author);

}
