package com.sametech.library_management_system.service.serviceInterface;

public interface IBookInstanceService {
    void borrowBookRequest(Long libraryUserId, String searchQuery);
    void approveBorrowBookRequest(Long bookInstanceId, Long librarianId);
    void returnBookRequest(Long libraryUserId, Long bookInstanceId);
    void approveReturnBookRequest(Long bookInstanceId, Long librarianId);

}
