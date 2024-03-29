package com.sametech.library_management_system.controller;

import com.sametech.library_management_system.data.dto.request.AuthenticationRequest;
import com.sametech.library_management_system.data.dto.request.PasswordRequest;
import com.sametech.library_management_system.data.dto.request.VerifyRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.AuthenticationResponse;
import com.sametech.library_management_system.data.dto.response.VerifyResponse;
import com.sametech.library_management_system.service.serviceInterface.IAppUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Application User")
@CrossOrigin(origins = "*")
public class AppUserController {
    private final IAppUserService appUserService;

    @GetMapping("/verify")
    public ResponseEntity<VerifyResponse>  verifyAccountWithToken(@RequestParam("email") String email,
                                                                  @RequestParam("token") String verificationToken){
        VerifyRequest request  = new VerifyRequest(email, verificationToken);
        return ResponseEntity.ok(appUserService.verifyAccountWithToken(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(appUserService.login(request));
    }
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        appUserService.refreshToken(request, response);
    }

    @PostMapping("/reset-password-mail")
    public ResponseEntity<ApiResponse> sendResetPasswordMail(@RequestParam String email){
        return ResponseEntity.ok(appUserService.sendResetPasswordMail(email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody PasswordRequest request){
        return ResponseEntity.ok(appUserService.resetPassword(request));
    }
}
