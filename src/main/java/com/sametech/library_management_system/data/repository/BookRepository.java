package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
