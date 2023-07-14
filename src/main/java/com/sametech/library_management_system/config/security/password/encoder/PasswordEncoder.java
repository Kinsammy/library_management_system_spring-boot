package com.sametech.library_management_system.config.security.password.encoder;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PasswordEncoder {
    @Bean
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
