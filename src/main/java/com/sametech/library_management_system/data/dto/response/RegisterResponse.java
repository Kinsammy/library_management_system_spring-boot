package com.sametech.library_management_system.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private Long id;
    private String message;
    private boolean isSuccess;
}
