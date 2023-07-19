package com.sametech.library_management_system.service.serviceInterface;

import com.github.fge.jsonpatch.JsonPatch;
import com.sametech.library_management_system.data.dto.request.RegisterRequest;
import com.sametech.library_management_system.data.dto.request.VerifyRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.RegisterResponse;
import com.sametech.library_management_system.data.dto.response.VerifyResponse;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.data.models.users.Librarian;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ILibraryUserService {
    RegisterResponse register(RegisterRequest request);



}
