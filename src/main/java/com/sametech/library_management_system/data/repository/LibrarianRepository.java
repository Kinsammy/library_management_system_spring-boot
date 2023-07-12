package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.users.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
}
