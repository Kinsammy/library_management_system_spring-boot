package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBookByTitleContainingIgnoreCase(String title);
    @Query("SELECT b FROM Book b WHERE CONCAT(b.author.firstName, ' ', b.author.lastName) LIKE %:author%")
    List<Book> findByAuthorNameContainingIgnoreCase(String author);

}
