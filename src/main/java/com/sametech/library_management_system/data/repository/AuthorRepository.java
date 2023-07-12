package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
