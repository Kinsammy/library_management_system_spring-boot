package com.sametech.library_management_system.config.security.providers;

import com.sametech.library_management_system.config.security.service.LibraryJpaUserDetailsService;
import com.sametech.library_management_system.config.security.user.details.LibrarySecuredUser;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class LibraryAuthenticationProvider implements AuthenticationProvider {
    private final LibraryJpaUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        LibrarySecuredUser userDetails = (LibrarySecuredUser) userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        if (passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                    userDetails.getPassword(), userDetails.getAuthorities());
        }
        throw new AuthenticationCredentialsNotFoundException("Incorrect username or password");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
