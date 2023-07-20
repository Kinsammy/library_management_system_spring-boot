package com.sametech.library_management_system.service.serviceInterface;

import com.sametech.library_management_system.data.dto.request.AuthenticationRequest;
import com.sametech.library_management_system.data.dto.request.PasswordRequest;
import com.sametech.library_management_system.data.dto.request.VerifyRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.AuthenticationResponse;
import com.sametech.library_management_system.data.dto.response.VerifyResponse;
import com.sametech.library_management_system.data.models.users.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IAppUserService {

    VerifyResponse verifyAccountWithToken(VerifyRequest request);
    AuthenticationResponse login(AuthenticationRequest request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
    ApiResponse sendVerifyLink(@NotNull AppUser user);

    ApiResponse sendResetPasswordMail(String email);
    ApiResponse resetPassword(PasswordRequest passwordRequest);
    ApiResponse updateAppUser(AppUser appUser);
    ApiResponse changePassword(PasswordRequest passwordRequest);
    AppUser getUserByEmail(String email);
    ApiResponse uploadProfileImage(Long userId, MultipartFile profileImage);
}
