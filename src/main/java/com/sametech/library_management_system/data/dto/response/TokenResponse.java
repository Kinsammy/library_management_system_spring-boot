package com.sametech.library_management_system.data.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private String generatedToken;
}
