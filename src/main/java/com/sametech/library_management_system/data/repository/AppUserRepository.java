package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.users.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}
