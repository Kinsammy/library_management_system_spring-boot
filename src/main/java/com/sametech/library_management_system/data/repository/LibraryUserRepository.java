package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.users.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {
}
