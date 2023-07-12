package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.users.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
}
