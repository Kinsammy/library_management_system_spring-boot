package com.sametech.library_management_system.controller;

import com.sametech.library_management_system.data.dto.request.AuthenticationRequest;
import com.sametech.library_management_system.data.dto.request.VerifyRequest;
import com.sametech.library_management_system.data.dto.response.AuthenticationResponse;
import com.sametech.library_management_system.data.dto.response.VerifyResponse;
import com.sametech.library_management_system.service.serviceInterface.IAppUserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor

public class AppUserController {
    private final IAppUserService appUserService;

    @PostMapping("/verify")
    public ResponseEntity<VerifyResponse>  verifyAccountWithToken(VerifyRequest request){
        return ResponseEntity.ok(appUserService.verifyAccountWithToken(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(appUserService.authenticate(request));
    }
}
