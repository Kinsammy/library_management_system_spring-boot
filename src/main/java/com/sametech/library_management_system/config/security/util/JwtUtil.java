package com.sametech.library_management_system.config.security.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtUtil {
    private final String jwtSecretKey;
}
