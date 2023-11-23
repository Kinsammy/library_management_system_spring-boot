package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibraryUserRepository extends JpaRepository<LibraryUser, Long> {
    Optional<LibraryUser> findLibraryUserByPhoneNumber(String phoneNumber);




}
