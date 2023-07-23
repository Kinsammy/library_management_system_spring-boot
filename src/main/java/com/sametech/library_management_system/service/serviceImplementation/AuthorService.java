package com.sametech.library_management_system.service.serviceImplementation;

import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.BookResponse;
import com.sametech.library_management_system.data.models.entity.Author;
import com.sametech.library_management_system.data.repository.AuthorRepository;
import com.sametech.library_management_system.exception.LibraryLogicException;
import com.sametech.library_management_system.service.serviceInterface.IAuthorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sametech.library_management_system.util.AppUtilities.NUMBER_OF_ITEMS_PER_PAGE;

@Service
@AllArgsConstructor
public class AuthorService implements IAuthorService {
    private final AuthorRepository authorRepository;


    @Override
    public BookResponse addNewAuthor(Author author) {
        authorRepository.save(author);
        return BookResponse.builder()
                .id(author.getId())
                .message("Author created successfully")
                .build();
    }

    @Override
    public Optional<Author> getAuthorById(Long authorId) {
        return authorRepository.findById(authorId);
    }

    @Override
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public ApiResponse updateAuthor(Author author) {
        authorRepository.findById(author.getId()).orElseThrow(()->
                new LibraryLogicException(
                        String.format("Author with id %d does not exist", author.getId())
                ));
        authorRepository.save(author);
        return ApiResponse.builder()
                .message("Author successfully updated")
                .build();
    }

    @Override
    public Page<Author> getAllAuthors(int pageNumber) {
        if (pageNumber < 1) pageNumber = 0;
        else pageNumber = pageNumber - 1;
        Pageable pageable = PageRequest.of(pageNumber, NUMBER_OF_ITEMS_PER_PAGE);
        return authorRepository.findAll(pageable);
    }

    @Override
    public void deleteAuthor(Long authorId) {
        authorRepository.deleteById(authorId);
    }
}
