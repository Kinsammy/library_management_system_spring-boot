package com.sametech.library_management_system.service.serviceImplementation;

import com.github.fge.jsonpatch.JsonPatch;
import com.sametech.library_management_system.data.dto.request.EmailNotificationRequest;
import com.sametech.library_management_system.data.dto.request.Recipient;
import com.sametech.library_management_system.data.dto.request.RegisterRequest;
import com.sametech.library_management_system.data.dto.request.VerifyRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.RegisterResponse;
import com.sametech.library_management_system.data.dto.response.TokenResponse;
import com.sametech.library_management_system.data.dto.response.VerifyResponse;
import com.sametech.library_management_system.data.models.token.Token;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.data.models.users.Librarian;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import com.sametech.library_management_system.data.repository.AppUserRepository;
import com.sametech.library_management_system.data.repository.LibraryUserRepository;
import com.sametech.library_management_system.exception.LibraryLogicException;
import com.sametech.library_management_system.exception.UserAlreadyExistException;
import com.sametech.library_management_system.exception.UserNotFoundException;
import com.sametech.library_management_system.notification.mail.IMailService;
import com.sametech.library_management_system.service.serviceInterface.IAppUserService;
import com.sametech.library_management_system.service.serviceInterface.ILibraryUserService;
import com.sametech.library_management_system.service.serviceInterface.ITokenService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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
        libraryUserDetails.setCreatedAt(LocalDateTime.now().toString());
        var libraryUser = LibraryUser.builder()
                .userDetails(libraryUserDetails)
                .build();
        LibraryUser savedLibraryUser = getsavedLibraryUser(libraryUser);
        var appUser = appUserRepository.save(libraryUserDetails);
        String token = tokenService.generateAndSaveToken(appUser);
        sendVerificationEmail(appUser, token);
        return getRegisterResponse(savedLibraryUser);
    }

//    @Override
//    public VerifyResponse verifyAccountWithToken(VerifyRequest request) {
//        LibraryUser libraryUser = new LibraryUser();
//       LibraryUser foundLibraryUser = getLibraryUserById(libraryUser.getId());
//       if (foundLibraryUser == null){
//           throw new UserNotFoundException("Invalid email");
//       }
//       Optional<Token> receivedToken = tokenService.validateReceivedToken(foundLibraryUser, request.getVerificationToken());
//       foundLibraryUser.getUserDetails().setEnabled(true);
//       libraryUserRepository.save(foundLibraryUser);
//       tokenService.deleteToken(receivedToken.get());
//        return getVerifyResponse();
//    }

        private static VerifyResponse getVerifyResponse() {
            return VerifyResponse.builder()
                    .message("Account verification successful")
                    .isSuccess(true)
                    .build();
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
        return libraryUserRepository.findById(libraryUserId).orElseThrow(
                ()-> new LibraryLogicException(
                        String.format("Library user with %d not found", libraryUserId)
                )
        );
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
