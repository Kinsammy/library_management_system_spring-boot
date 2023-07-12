package com.sametech.library_management_system.service.serviceInterface;

import com.sametech.library_management_system.data.dto.request.VerifyRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.VerifyResponse;
import com.sametech.library_management_system.data.models.users.AppUser;
import org.springframework.web.multipart.MultipartFile;

public interface IAppUserUserService {
    VerifyResponse verifyAccountWithToken(VerifyRequest request);
    AppUser getUserByEmail(String email);
    ApiResponse uploadProfileImage(Long userId, MultipartFile profileImage);
}
