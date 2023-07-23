package com.sametech.library_management_system.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.sametech.library_management_system.data.dto.request.RegisterRequest;
import com.sametech.library_management_system.data.dto.response.RegisterResponse;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import com.sametech.library_management_system.service.serviceInterface.ILibraryUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/library-user")
@AllArgsConstructor
public class LibraryUserController {
    private final ILibraryUserService libraryUserService;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request){
        var response = libraryUserService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("{libraryUserId}")
    public ResponseEntity<?> getLibraryUserById(@PathVariable Long libraryUserId){
        var foundLibraryUser = libraryUserService.getLibraryUserById(libraryUserId);
        return ResponseEntity.status(HttpStatus.OK).body(foundLibraryUser);
    }

    @GetMapping("/all/{pageNumber}")
    public ResponseEntity<?> getAllLibraryUsers(@PathVariable int pageNumber){
        var response = libraryUserService.getAllLibraryUsersPerPage(pageNumber);
        return ResponseEntity.ok(response.getContent());
    }

    @PutMapping("/library-user")
    public ResponseEntity<?> updateLibraryUser(@RequestBody LibraryUser libraryUser){
        var response = libraryUserService.updateLibraryUser(libraryUser);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PatchMapping(value = "{libraryUserId}", consumes = {"application/json-patch+json"})
    public ResponseEntity<?> updateLibraryUser(@PathVariable Long libraryUserId, @RequestBody JsonPatch updatePatch){
        try{
            var response = libraryUserService.updateLibraryUser(libraryUserId, updatePatch);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("{libraryUserId}")
    public void deleteLibraryUser(
            @PathVariable("libraryUserId")
            Long libraryUserId){
        libraryUserService.deleteLibraryUser(libraryUserId);
    }
}
