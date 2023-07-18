package com.sametech.library_management_system.service.serviceImplementation;

import com.sametech.library_management_system.config.security.service.JwtService;
import com.sametech.library_management_system.data.dto.request.AuthenticationRequest;
import com.sametech.library_management_system.data.dto.request.PasswordRequest;
import com.sametech.library_management_system.data.dto.request.VerifyRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.AuthenticationResponse;
import com.sametech.library_management_system.data.dto.response.VerifyResponse;
import com.sametech.library_management_system.data.models.token.Token;
import com.sametech.library_management_system.data.models.token.TokenType;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.data.repository.AppUserRepository;
import com.sametech.library_management_system.data.repository.TokenRepository;
import com.sametech.library_management_system.exception.LibraryLogicException;
import com.sametech.library_management_system.exception.UserNotFoundException;
import com.sametech.library_management_system.service.serviceInterface.IAppUserService;
import com.sametech.library_management_system.service.serviceInterface.ITokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppUserService implements IAppUserService {
    private final AppUserRepository appUserRepository;
    private final ITokenService tokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;


    @Override
    public VerifyResponse verifyAccountWithToken(VerifyRequest request) {
        if (getUserByEmail(request.getEmail()) == null) throw new UserNotFoundException("Invalid email");
        AppUser appUser = getUserByEmail(request.getEmail());
        Optional<Token> receivedToken = tokenService.validateReceivedToken(appUser, request.getVerificationToken());
        appUser.setEnabled(true);
        appUserRepository.save(appUser);
        tokenService.deleteToken(receivedToken.get());
        return getVerifyResponse();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var appUser = appUserRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(appUser);
        revokeUserToken(appUser);
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

    private void revokeUserToken(AppUser appUser) {
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
                .message("Account verification successful")
                .isSuccess(true)
                .build();
    }

    @Override
    public ApiResponse sendVerifyLink(@NotNull AppUser user) {
        return null;
    }


    @Override
    public void sendResetPasswordMail(String email) {

    }

    @Override
    public ApiResponse resetPassword(PasswordRequest passwordRequest) {
        return null;
    }

    @Override
    public ApiResponse changePassword(PasswordRequest passwordRequest) {
        return null;
    }

    @Override
    public AppUser getUserByEmail(String email) {
        return appUserRepository.findByEmail(email).orElseThrow(
                ()-> new LibraryLogicException(String.format("Email: %s not found", email)));
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public ApiResponse uploadProfileImage(Long userId, MultipartFile profileImage) {
        return null;
    }
}
