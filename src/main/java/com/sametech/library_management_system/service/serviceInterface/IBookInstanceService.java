package com.sametech.library_management_system.service.serviceInterface;

import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.models.entity.Book;
import com.sametech.library_management_system.data.models.entity.BookInstance;

import java.util.List;

public interface IBookInstanceService {
    ApiResponse borrowBookRequest(Long libraryUserId, String title);
    List<BookInstance> getAvailableBookInstancesByBook(Book book);
    ApiResponse approveBorrowBookRequest(Long bookInstanceId, Long librarianId);
    ApiResponse returnBookRequest(Long libraryUserId, Long bookInstanceId);
    ApiResponse approveReturnBookRequest(Long bookInstanceId, Long librarianId);

}
