package com.sametech.library_management_system.service.serviceImplementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sametech.library_management_system.config.security.service.JwtService;
import com.sametech.library_management_system.data.dto.request.*;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.AuthenticationResponse;
import com.sametech.library_management_system.data.dto.response.RegisterResponse;
import com.sametech.library_management_system.data.dto.response.VerifyResponse;
import com.sametech.library_management_system.data.models.token.Token;
import com.sametech.library_management_system.data.models.token.TokenType;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.data.repository.AppUserRepository;
import com.sametech.library_management_system.data.repository.TokenRepository;
import com.sametech.library_management_system.exception.LibraryAuthenticationException;
import com.sametech.library_management_system.exception.LibraryLogicException;
import com.sametech.library_management_system.exception.UserNotFoundException;
import com.sametech.library_management_system.notification.mail.IMailService;
import com.sametech.library_management_system.service.serviceInterface.IAppUserService;
import com.sametech.library_management_system.service.serviceInterface.ITokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserService implements IAppUserService {
    private final AppUserRepository appUserRepository;
    private final ITokenService tokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final IMailService mailService;


    @Override
    public AuthenticationResponse createAdminAndLibrarian(RegisterRequest request) {
        var user = AppUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .enabled(true)
                .build();
    var savedUser = appUserRepository.save(user);
    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(savedUser, jwtToken);
    var refreshToken = jwtService.generateRefreshToken(user);
    return getAuthenticationResponse(jwtToken, refreshToken);
    }

    @Override
    public VerifyResponse verifyAccountWithToken(VerifyRequest request) {
        String userEmail = request.getEmail();

        if (userEmail == null)
            throw new UserNotFoundException("Email cannot be null");

        AppUser appUser = getUserByEmail(userEmail);
        Optional<Token> receivedToken = tokenService.validateReceivedToken(appUser, request.getVerificationToken());

        appUser.setEnabled(true);
        appUserRepository.save(appUser);

        tokenService.deleteToken(receivedToken.get());

        return getVerifyResponse();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var appUser = appUserRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(appUser);
        revokeAllUserTokens(appUser);
        saveUserToken(appUser, jwtToken);
        var refreshToken = jwtService.generateRefreshToken(appUser);
        return getAuthenticationResponse(jwtToken, refreshToken);
    }

    private static AuthenticationResponse getAuthenticationResponse(String jwtToken, String refreshToken) {
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(AppUser appUser, String jwtToken) {
        var token = Token.builder()
                .appUser(appUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(AppUser appUser) {
        var validUserTokens = tokenRepository.findValidTokenByAppUserId(appUser.getId());
        if (validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private static VerifyResponse getVerifyResponse() {
        return VerifyResponse.builder()
                .message("Email verified successfully. Now you can login to your account")
                .isSuccess(true)
                .build();
    }

    @Override
    public ApiResponse sendVerifyLink(@NotNull AppUser user) {
        return null;
    }


    @Override
    public ApiResponse sendResetPasswordMail(String email) {
        var appUser = appUserRepository.findByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        String generateToken = tokenService.generateAndSaveToken(appUser);
        sendResetNotification(appUser, generateToken);
        return ApiResponse.builder()
                .message("Check your email to get your token")
                .build();

    }

    private void sendResetNotification(AppUser appUser, String token) {
        EmailNotificationRequest request = new EmailNotificationRequest();
        request.getTo().add(new Recipient(
                appUser.getUsername(), appUser.getEmail()));
        request.setSubject("Welcome to SamTech: Reset Your Password");
        request.setHtmlContent("To reset your password enter the following digits on your web browser\n\n" + token);
        mailService.sendMail(request);
    }

    @Override
    public ApiResponse resetPassword(PasswordRequest passwordRequest) {
        Optional<Token> token = tokenRepository.findTokenByAppUserAndToken(getUserByEmail(passwordRequest.getEmail()),
                passwordRequest.getVerificationToken());

        if (token.isEmpty()) throw new LibraryLogicException("Invalid or expired reset token");
        Token validToken = token.get();
        if (validToken.getExpiryTime().isBefore(LocalDateTime.now())){
            tokenRepository.delete(token.get());
            throw new LibraryAuthenticationException("Reset token has expired");
        }
        var appUser = validToken.getAppUser();
        if (!appUser.getEmail().equals(passwordRequest.getEmail())){
            throw new LibraryLogicException(String.format("Invalid token for %s", passwordRequest.getEmail()));
        }

        appUser.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        updateAppUser(appUser);
        tokenRepository.delete(validToken);
        return ApiResponse.builder()
                .message("Password has reset successful")
                .build();
    }

    @Override
    public ApiResponse updateAppUser(AppUser appUser) {
        appUserRepository.save(appUser);
        return ApiResponse.builder()
                .message("Successfully updated")
                .build();
    }

    @Override
    public ApiResponse changePassword(PasswordRequest passwordRequest) {
        return null;
    }

    @Override
    public AppUser getUserByEmail(String email) {
        return appUserRepository.findByEmail(email).orElseThrow(
                ()-> new UserNotFoundException(String.format("User not found for email: %s", email)));
    }

    @Override
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null){
            var appUser = appUserRepository.findByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, appUser)){
                var accessToken = jwtService.generateToken(appUser);
                revokeAllUserTokens(appUser);
                saveUserToken(appUser, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    public ApiResponse uploadProfileImage(Long userId, MultipartFile profileImage) {
        return null;
    }

    @Override
    public RegisterResponse getRegisterResponse(AppUser savedLibraryUser) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(savedLibraryUser.getId());
        registerResponse.setSuccess(true);
        registerResponse.setMessage("User Registration is successful. Check your email to get your token");
        return registerResponse;
    }
    @Override
    public boolean emailExists(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }
}
