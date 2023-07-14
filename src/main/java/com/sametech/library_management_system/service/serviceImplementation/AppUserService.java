package com.sametech.library_management_system.service.serviceImplementation;

import com.sametech.library_management_system.data.dto.request.PasswordRequest;
import com.sametech.library_management_system.data.dto.request.VerifyRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.VerifyResponse;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.data.repository.AppUserRepository;
import com.sametech.library_management_system.service.serviceInterface.IAppUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class AppUserService implements IAppUserService {
    private final AppUserRepository appUserRepository;

    @Override
    public VerifyResponse verifyAccountWithToken(VerifyRequest request) {
        return null;
    }

    @Override
    public ApiResponse sendVerifyLink(@NotNull AppUser user) {
        return null;
    }

    @Override
    public ApiResponse sendVerifyOtp(@NotNull AppUser user) {
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
        return appUserRepository.findByEmail(email)
                .orElseThrow(()->
                        new UsernameNotFoundException(
                                "User with email address not found"
                        ));
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public ApiResponse uploadProfileImage(Long userId, MultipartFile profileImage) {
        return null;
    }
}
