package com.sametech.library_management_system.service.serviceInterface;

import com.sametech.library_management_system.data.dto.request.PasswordRequest;
import com.sametech.library_management_system.data.dto.request.VerifyRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.VerifyResponse;
import com.sametech.library_management_system.data.models.users.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public interface IAppUserUserService {
    VerifyResponse verifyAccountWithToken(VerifyRequest request);
    ApiResponse sendVerifyLink(@NotNull AppUser user);
    ApiResponse sendVerifyOtp(@NotNull AppUser user);
    void sendResetPasswordMail(String email);
    ApiResponse resetPassword(PasswordRequest passwordRequest);
    ApiResponse changePassword(PasswordRequest passwordRequest);
    AppUser getUserByEmail(String email);
    void refreshToken(HttpServletRequest request, HttpServletResponse response);
    ApiResponse uploadProfileImage(Long userId, MultipartFile profileImage);
}
