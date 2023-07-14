package com.sametech.library_management_system.config.security.manager;

import com.sametech.library_management_system.config.security.providers.LibraryAuthenticationProvider;
import com.sametech.library_management_system.exception.LibraryAuthenticationException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LibraryAuthenticationManager implements AuthenticationManager {
    private final AuthenticationProvider authenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws LibraryAuthenticationException {
        return authenticationProvider.authenticate(authentication);
    }
}
