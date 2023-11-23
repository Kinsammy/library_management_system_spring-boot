package com.sametech.library_management_system.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PasswordRequest {
    private String email;
    private String verificationToken;
    private String newPassword;

}
