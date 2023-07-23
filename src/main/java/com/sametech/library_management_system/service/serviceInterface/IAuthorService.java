package com.sametech.library_management_system.service.serviceInterface;

import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.BookResponse;
import com.sametech.library_management_system.data.models.entity.Author;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IAuthorService {
    BookResponse addNewAuthor(Author author);
    Optional<Author> getAuthorById(Long authorId);
    void saveAuthor(Author author);
    ApiResponse updateAuthor(Author author);
    Page<Author> getAllAuthors(int pageNumber);
    void deleteAuthor(Long authorId);
}
