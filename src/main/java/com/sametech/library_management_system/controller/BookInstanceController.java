package com.sametech.library_management_system.controller;

import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.service.serviceInterface.IBookInstanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book-instances")
@AllArgsConstructor
@Slf4j
public class BookInstanceController {
    private final IBookInstanceService bookInstanceService;

    @PostMapping("/borrow-book/{libraryUserId}/{title}")
    public ResponseEntity<ApiResponse> borrowBookRequest(@PathVariable Long libraryUserId, @PathVariable String title){
        var response = bookInstanceService.borrowBookRequest(libraryUserId, title);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/approve-borrow/{bookInstanceId}/{librarianId}")
    public ResponseEntity<ApiResponse> approveBorrowBookRequest(@PathVariable Long bookInstanceId, @PathVariable Long librarianId){
        var response = bookInstanceService.approveBorrowBookRequest(bookInstanceId, librarianId);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/return-book/{libraryUserId}/{bookInstanceId}")
    public ResponseEntity<ApiResponse> returnBookRequest(@PathVariable Long libraryUserId, @PathVariable Long bookInstanceId){
        var response = bookInstanceService.returnBookRequest(libraryUserId, bookInstanceId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/approve-return/{bookInstanceId}/{librarianId}")
    public ResponseEntity<ApiResponse> approveReturnBookRequest(@PathVariable Long bookInstanceId, @PathVariable Long librarianId){
        var response = bookInstanceService.approveReturnBookRequest(bookInstanceId, librarianId);
        return ResponseEntity.ok(response);
    }


}
