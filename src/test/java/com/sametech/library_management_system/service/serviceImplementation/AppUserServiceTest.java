package com.sametech.library_management_system.service.serviceImplementation;

import com.sametech.library_management_system.data.dto.request.AuthenticationRequest;
import com.sametech.library_management_system.data.dto.request.VerifyRequest;
import com.sametech.library_management_system.data.repository.AppUserRepository;
import com.sametech.library_management_system.service.serviceInterface.IAppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserServiceTest {
    @Autowired
    private IAppUserService appUserService;

    private VerifyRequest verifyRequest;
    @Autowired
    private AuthenticationRequest request;

    @BeforeEach
    void setUp(){
        verifyRequest = new VerifyRequest();
        verifyRequest.setEmail("fanusamuel@gmail.com");
        verifyRequest.setVerificationToken("BmUK60jr0LHAVr_-dZMv0bkjVcVCdmsqn5WmOEW21oTC01QrmtftBen_LtbD2nvTvHkDUdLR_vN_ebIzAcTY8g");
    }
    @Test
    void verifyAccountWithToken() {
        var verifyResponse = appUserService.verifyAccountWithToken(verifyRequest);
        assertThat(verifyResponse.isSuccess()).isTrue();
    }

    @Test
    void authenticate() {
    }

    @Test
    void sendVerifyLink() {
    }

    @Test
    void sendResetPasswordMail() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void getUserByEmailTest() {
    }

    @Test
    void refreshToken() {
    }

    @Test
    void uploadProfileImage() {
    }


}