package com.sametech.library_management_system.service.serviceInterface;

import com.github.fge.jsonpatch.JsonPatch;
import com.sametech.library_management_system.data.dto.request.RegisterRequest;
import com.sametech.library_management_system.data.dto.response.RegisterResponse;
import com.sametech.library_management_system.data.models.users.Librarian;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ILibraryUserService {
    RegisterResponse register(RegisterRequest request);

    LibraryUser getLibraryUserById(Long libraryUserId);

    Optional<LibraryUser> getLibraryBy(Long libraryUserId);
    LibraryUser updateLibraryUser(Long libraryUserId, JsonPatch updatePayLoad);
    Page<LibraryUser> getAllLibraryUsersPerPage(int pageNumber);
    LibraryUser updateLibraryUser(LibraryUser libraryUser);
    Librarian deleteLibraryUser(Long  libraryUserId);
}
