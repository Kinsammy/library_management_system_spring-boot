package com.sametech.library_management_system.service.serviceImplementation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.sametech.library_management_system.data.dto.request.EmailNotificationRequest;
import com.sametech.library_management_system.data.dto.request.Recipient;
import com.sametech.library_management_system.data.dto.request.RegisterRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.RegisterResponse;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import com.sametech.library_management_system.data.repository.AppUserRepository;
import com.sametech.library_management_system.data.repository.LibraryUserRepository;
import com.sametech.library_management_system.exception.LibraryLogicException;
import com.sametech.library_management_system.exception.UserAlreadyExistException;
import com.sametech.library_management_system.notification.mail.IMailService;
import com.sametech.library_management_system.service.serviceInterface.IAppUserService;
import com.sametech.library_management_system.service.serviceInterface.ILibraryUserService;
import com.sametech.library_management_system.service.serviceInterface.ITokenService;
import com.sametech.library_management_system.util.AppUtilities;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.sametech.library_management_system.util.AppUtilities.NUMBER_OF_ITEMS_PER_PAGE;

@AllArgsConstructor
@Slf4j
@Service
public class LibraryUserService implements ILibraryUserService {
    private final LibraryUserRepository libraryUserRepository;
    private final AppUserRepository appUserRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final IMailService mailService;
    private final IAppUserService userService;
    private final ITokenService tokenService;
    
    @Override
    public RegisterResponse register(RegisterRequest request) {
        if (userService.emailExists(request.getEmail())){
            throw new UserAlreadyExistException(
                    String.format("There is an account with %s", request.getEmail())
            );
        }
        var libraryUserDetails = modelMapper.map(request, AppUser.class);
        var libraryUser = new LibraryUser();
        getUserDetails(request, libraryUserDetails, libraryUser);
        var appUser = appUserRepository.save(libraryUserDetails);
        String token = tokenService.generateAndSaveToken(appUser);
        sendVerificationEmail(appUser, token);
        return userService.getRegisterResponse(appUser);
    }

    @Override
    public LibraryUser getLibraryUserById(Long libraryUserId) {
        return libraryUserRepository.findById(libraryUserId).orElseThrow(()->
                new LibraryLogicException(
                        String.format("Library User with id %d not found", libraryUserId)
                ));
    }

    @Override
    public void saveLibraryUser(LibraryUser libraryUser) {
        libraryUserRepository.save(libraryUser);
    }

    @Override
    public Optional<LibraryUser> getLibraryUserBy(Long libraryUserId) {
        return libraryUserRepository.findById(libraryUserId);
    }


    @Override
    public ApiResponse updateLibraryUser(Long libraryUserId, JsonPatch updatePayLoad) {
        ObjectMapper mapper = new ObjectMapper();
        LibraryUser foundLibraryUser = getLibraryUserById(libraryUserId);
        AppUser libraryUserDetails = foundLibraryUser.getUserDetails();
        JsonNode node = mapper.convertValue(foundLibraryUser, JsonNode.class);
        try{
            JsonNode updatedNode = updatePayLoad.apply(node);
            var updatedLibraryUser = mapper.convertValue(updatedNode, LibraryUser.class);
            libraryUserRepository.save(updatedLibraryUser);
            return getUpdateResponse();
        } catch (JsonPatchException exception){
            throw new LibraryLogicException("Library details is not successfully updated");
        }
    }

    @Override
    public Page<LibraryUser> getAllLibraryUsersPerPage(int pageNumber) {
        if (pageNumber<1) pageNumber = 0;
        else pageNumber = pageNumber-1;
        Pageable pageable = PageRequest.of(pageNumber, NUMBER_OF_ITEMS_PER_PAGE);
        return libraryUserRepository.findAll(pageable);
    }

    @Override
    public ApiResponse updateLibraryUser(LibraryUser libraryUser) {
        libraryUserRepository.findById(libraryUser.getId()).orElseThrow(()->
                new LibraryLogicException(
                        String.format("Library User with id %d does not exist", libraryUser.getId())
                )
        );
        libraryUserRepository.save(libraryUser);
        return getUpdateResponse();
    }

    private static ApiResponse getUpdateResponse() {
        return ApiResponse.builder()
                .message("Library User's successfully updated")
                .build();
    }


    @Override
    public void deleteLibraryUser(Long libraryUserId) {
        boolean isExist = libraryUserRepository.existsById(libraryUserId);
        if (!isExist){
            throw new LibraryLogicException(
                    String.format("Library user with id %d does not exist", libraryUserId)
            );
        }
        libraryUserRepository.deleteById(libraryUserId);
    }

    private void getUserDetails(RegisterRequest request, AppUser libraryUserDetails, LibraryUser libraryUser) {
        libraryUserDetails.setFirstName(request.getFirstName());
        libraryUserDetails.setLastName(request.getLastName());
        libraryUserDetails.setEmail(request.getEmail());
        libraryUserDetails.setPassword(passwordEncoder.encode(request.getPassword()));
        libraryUserDetails.setRole(request.getRole());
        libraryUserDetails.setCreatedAt(LocalDateTime.now().toString());
        libraryUser.setUserDetails(libraryUserDetails);
        saveLibraryUser(libraryUser);
    }





    private void sendVerificationEmail(AppUser appUser, String token) {
        EmailNotificationRequest request = new EmailNotificationRequest();

        request.getTo().add(new Recipient(
               appUser.getFirstName(),
               appUser.getEmail())
        );
        request.setSubject("Welcome to SamTech: Activate Your Account");
        request.setHtmlContent("To activate your Account enter the following digits on your web browser\n\n" + token);
        mailService.sendMail(request);
    }



   




}
