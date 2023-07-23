package com.sametech.library_management_system.controller;

import com.sametech.library_management_system.data.dto.request.RegisterRequest;
import com.sametech.library_management_system.data.dto.response.RegisterResponse;
import com.sametech.library_management_system.service.serviceInterface.ILibrarianService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/library-user")
@AllArgsConstructor
public class LibrarianController {
    private final ILibrarianService librarianService;

    @PostMapping("/create-librarian")
    public ResponseEntity<RegisterResponse> createLibrarian(@RequestBody RegisterRequest request){
        var response = librarianService.createLibrarian(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{librarianId}")
    public ResponseEntity<?> getLibrarianById(@PathVariable Long librarianId){
        var foundLibrarian = librarianService.getLibrarianById(librarianId);
        return ResponseEntity.status(HttpStatus.OK).body(foundLibrarian);
    }
}
