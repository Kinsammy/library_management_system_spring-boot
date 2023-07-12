package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.entity.BookInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInstanceRepository extends JpaRepository<BookInstance, Long> {
}
