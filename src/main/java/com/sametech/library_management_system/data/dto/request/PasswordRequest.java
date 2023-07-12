package com.sametech.library_management_system.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PasswordRequest {
    private String email;
    private String verificationToken;
    private String newPassword;
}
