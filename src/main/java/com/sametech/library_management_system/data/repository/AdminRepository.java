package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
