package com.sametech.library_management_system.service.serviceImplementation;

import com.github.fge.jsonpatch.JsonPatch;
import com.sametech.library_management_system.data.dto.request.RegisterRequest;
import com.sametech.library_management_system.data.dto.response.RegisterResponse;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.data.models.users.Librarian;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import com.sametech.library_management_system.data.repository.AppUserRepository;
import com.sametech.library_management_system.data.repository.LibraryUserRepository;
import com.sametech.library_management_system.exception.UserAlreadyExistException;
import com.sametech.library_management_system.service.serviceInterface.ILibraryUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class LibraryUserService implements ILibraryUserService {
    private final LibraryUserRepository libraryUserRepository;
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (emailExists(request.getEmail())){
            throw new UserAlreadyExistException(
                    String.format("There is an account with %s", request.getEmail())
            );
        }
        var libraryUserDetails = modelMapper.map(request, AppUser.class);
        libraryUserDetails.setFirstName(request.getFirstName());
        libraryUserDetails.setLastName(request.getLastName());
        libraryUserDetails.setEmail(request.getEmail());
        libraryUserDetails.setPassword(passwordEncoder.encode(request.getPassword()));
        libraryUserDetails.setRole(request.getRole());
        var libraryUser = LibraryUser.builder()
                .userDetails(libraryUserDetails)
                .build();
        LibraryUser savedLibraryUser = getsavedLibraryUser(libraryUser);
        return getRegisterResponse(savedLibraryUser);
    }

    private RegisterResponse getRegisterResponse(LibraryUser savedLibraryUser) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(savedLibraryUser.getId());
        registerResponse.setSuccess(true);
        registerResponse.setMessage("User Registration Successful");
        return registerResponse;
    }

    private boolean emailExists(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }

    @Override
    public LibraryUser getLibraryUserById(Long libraryUserId) {
        return null;
    }


    private  LibraryUser getsavedLibraryUser(LibraryUser libraryUser) {
        return libraryUserRepository.save(libraryUser);
    }

    @Override
    public Optional<LibraryUser> getLibraryBy(Long libraryUserId) {
        return Optional.empty();
    }

    @Override
    public LibraryUser updateLibraryUser(Long libraryUserId, JsonPatch updatePayLoad) {
        return null;
    }

    @Override
    public Page<LibraryUser> getAllLibraryUsersPerPage(int pageNumber) {
        return null;
    }

    @Override
    public LibraryUser updateLibraryUser(LibraryUser libraryUser) {
        return null;
    }

    @Override
    public Librarian deleteLibraryUser(Long libraryUserId) {
        return null;
    }
}
