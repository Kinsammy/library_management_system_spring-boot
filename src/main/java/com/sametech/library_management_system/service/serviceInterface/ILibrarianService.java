package com.sametech.library_management_system.service.serviceInterface;

import com.github.fge.jsonpatch.JsonPatch;
import com.sametech.library_management_system.data.dto.request.BookRequest;
import com.sametech.library_management_system.data.dto.request.RegisterRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.BookResponse;
import com.sametech.library_management_system.data.dto.response.RegisterResponse;
import com.sametech.library_management_system.data.models.users.Librarian;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ILibrarianService {

    RegisterResponse createLibrarian(RegisterRequest request);
    Librarian getLibrarianById(Long librarianId);
    void saveLibrarian(Librarian librarian);
    Optional<Librarian> getLibraryBy(Long librarianId);
    ApiResponse updateLibrarian(Long librarianId, JsonPatch updatePayLoad);
    Page<Librarian> getAllLibrariansPerPage(int pageNumber);
    ApiResponse updateLibrarian(Librarian librarian);
    void deleteLibrarian(Long  librarianId);

}
