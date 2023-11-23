package com.sametech.library_management_system.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.sametech.library_management_system.data.dto.request.RegisterRequest;
import com.sametech.library_management_system.data.dto.response.RegisterResponse;
import com.sametech.library_management_system.data.models.users.Librarian;
import com.sametech.library_management_system.service.serviceInterface.ILibrarianService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/librarian")
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

    @GetMapping("/all/{pageNumber}")
    public ResponseEntity<?> getAllLibrarian(@PathVariable int pageNumber){
        var response = librarianService.getAllLibrariansPerPage(pageNumber);
        return ResponseEntity.ok(response.getContent());
    }

    @PutMapping("/librarian")
    public ResponseEntity<?> updateLibrarian(@RequestBody Librarian librarian){
        var response = librarianService.updateLibrarian(librarian);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PatchMapping(value = "{librarianId}", consumes = {"application/json-patch+json"})
    public ResponseEntity<?> updateLibrarian(@PathVariable Long librarianId, @RequestBody JsonPatch updatePatch){
        try{
            var response = librarianService.updateLibrarian(librarianId, updatePatch);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{librarianId}")
    public void deleteLibrarian(@PathVariable("librarianId") Long librarianId){
        librarianService.deleteLibrarian(librarianId);
    }


}
