package com.sametech.library_management_system.service.serviceImplementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sametech.library_management_system.data.dto.request.RegisterRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.RegisterResponse;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.data.models.users.Librarian;
import com.sametech.library_management_system.data.repository.LibrarianRepository;
import com.sametech.library_management_system.exception.LibraryLogicException;
import com.sametech.library_management_system.service.serviceInterface.IAppUserService;
import com.sametech.library_management_system.service.serviceInterface.ILibrarianService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.sametech.library_management_system.util.AppUtilities.NUMBER_OF_ITEMS_PER_PAGE;

@Service
@AllArgsConstructor
public class LibrarianService implements ILibrarianService {
    private final LibrarianRepository librarianRepository;
    private final IAppUserService appUserService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse createLibrarian(RegisterRequest request) {
        if (appUserService.emailExists(request.getEmail())){
            throw new LibraryLogicException(
                    String.format("There is an account with %s", request.getEmail())
            );
        }
        var librarianDetails = modelMapper.map(request, AppUser.class);
        var librarian = new Librarian();
        getLibrarianDetails(request, librarianDetails, librarian);
        return appUserService.getRegisterResponse(librarianDetails);
    }

    private void getLibrarianDetails(RegisterRequest request, AppUser librarianDetails, Librarian librarian) {
        librarianDetails.setFirstName(request.getFirstName());
        librarianDetails.setLastName(request.getLastName());
        librarianDetails.setEmail(request.getEmail());
        librarianDetails.setPassword(passwordEncoder.encode(request.getPassword()));
        librarianDetails.setRole(request.getRole());
        librarianDetails.setCreatedAt(LocalDateTime.now().toString());
        librarian.setUserDetails(librarianDetails);
        saveLibrarian(librarian);
    }


    @Override
    public Librarian getLibrarianById(Long librarianId) {
        return librarianRepository.findById(librarianId).orElseThrow(()->
                new LibraryLogicException(
                        String.format("Librarian with id %d not found", librarianId)
                ));

    }

    @Override
    public void saveLibrarian(Librarian librarian) {
        librarianRepository.save(librarian);
    }

    @Override
    public Optional<Librarian> getLibraryBy(Long librarianId) {
        return librarianRepository.findById(librarianId);
    }

    @Override
    public ApiResponse updateLibrarian(Long librarianId, JsonPatch updatePayLoad) {
        ObjectMapper mapper = new ObjectMapper();
        Librarian foundLibrarian = getLibrarianById(librarianId);
        AppUser librarianDetails = foundLibrarian.getUserDetails();
        JsonNode node = mapper.convertValue(foundLibrarian, JsonNode.class);
        try {
            JsonNode updatedNode = updatePayLoad.apply(node);
            var updatedLibrarian = mapper.convertValue(updatedNode, Librarian.class);
            librarianRepository.save(updatedLibrarian);
            return getUpdateResponse();
        } catch (JsonPatchException exception){
            throw new LibraryLogicException("Librarian details is not successfully updated");
        }
    }
    private static ApiResponse getUpdateResponse(){
        return ApiResponse.builder()
                .message("Librarian's successfully updated")
                .build();
    }

    @Override
    public Page<Librarian> getAllLibrariansPerPage(int pageNumber) {
        if (pageNumber < 1) pageNumber = 0;
        else pageNumber = pageNumber - 1;
        Pageable pageable = PageRequest.of(pageNumber, NUMBER_OF_ITEMS_PER_PAGE);
        return librarianRepository.findAll(pageable);
    }

    @Override
    public ApiResponse updateLibrarian(Librarian librarian) {
        librarianRepository.findById(librarian.getId()).orElseThrow(()->
                new LibraryLogicException(
                        String.format("Librarian with id %d does not exist", librarian.getId())
                ));
        librarianRepository.save(librarian);
        return getUpdateResponse();
    }

    @Override
    public void deleteLibrarian(Long librarianId) {
        boolean isExist = librarianRepository.existsById(librarianId);
        if (!isExist){
            throw new LibraryLogicException(
                    String.format("Librarian with id %d does not exist", librarianId)
            );
        }
    }
}
