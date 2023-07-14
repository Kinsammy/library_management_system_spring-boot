package com.sametech.library_management_system.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sametech.library_management_system.config.security.manager.LibraryAuthenticationManager;
import com.sametech.library_management_system.config.security.util.JwtUtil;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.exception.LibraryAuthenticationException;
import com.sametech.library_management_system.exception.LibraryLogicException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@AllArgsConstructor
@Component
public class LibraryMSAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final LibraryAuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws LibraryAuthenticationException {
        ObjectMapper mapper = new ObjectMapper();
        AppUser user;
        try{
            user = mapper.readValue(request.getInputStream(), AppUser.class);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            Authentication authenticationResult =
                    authenticationManager.authenticate(authentication);
            if (authenticationResult != null) {
                return getAuthentication(authenticationResult);
            }
        } catch (IOException exception){
            throw new LibraryLogicException(exception.getMessage());
        }
    }



// todo-> Security Context
    private Authentication getAuthentication(Authentication authenticationResult) {
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
