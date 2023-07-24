package com.sametech.library_management_system.controller;

import com.sametech.library_management_system.data.dto.request.AuthorRequest;
import com.sametech.library_management_system.data.dto.response.BookResponse;
import com.sametech.library_management_system.data.models.entity.Author;
import com.sametech.library_management_system.service.serviceInterface.IAuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("'/api/v1/authors")
@AllArgsConstructor
public class AuthorController {
    private final IAuthorService authorService;

    @PostMapping("/add")
    public ResponseEntity<BookResponse> addNewAuthor(@RequestBody AuthorRequest request){
        var response = authorService.addNewAuthor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{authorId}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long authorId){
        var foundAuthor = authorService.getAuthorById(authorId);
        return ResponseEntity.status(HttpStatus.OK).body(foundAuthor);
    }

    @GetMapping("/all/{pageNumber}")
    public ResponseEntity<?> getAllAuthors(@PathVariable int pageNumber){
        var response = authorService.getAllAuthors(pageNumber);
        return ResponseEntity.ok(response.getContent());
    }

    @PutMapping("/author")
    public ResponseEntity<?> updateAuthor(@RequestBody Author author){
        var response = authorService.updateAuthor(author);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @DeleteMapping("{authorId}")
    public void deleteAuthor(@PathVariable("authorId") Long authorId){
        authorService.deleteAuthor(authorId);
    }
}
