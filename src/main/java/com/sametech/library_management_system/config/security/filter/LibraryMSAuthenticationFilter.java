package com.sametech.library_management_system.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sametech.library_management_system.config.security.manager.LibraryAuthenticationManager;
import com.sametech.library_management_system.config.security.user.details.LibrarySecuredUser;
import com.sametech.library_management_system.config.security.util.JwtUtil;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.exception.LibraryAuthenticationException;
import com.sametech.library_management_system.exception.LibraryLogicException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LibraryMSAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
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
        throw new LibraryLogicException("oops!");
    }



// todo-> Security Context
    private Authentication getAuthentication(Authentication authenticationResult) {
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> claims = authResult.getAuthorities()
                .stream()
                .collect(Collectors.toMap(k-> "claim", v->v));

        String accessToken = Jwts.builder()
                .setIssuer("SameTech")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now()
                        .plusSeconds(BigInteger.valueOf(60).longValue()*
                                BigInteger.valueOf(60).intValue())))
                .signWith(getSignInKey(), SignatureAlgorithm.ES512)
                .compact();

        String refreshToken = Jwts.builder()
                .setIssuer("SamTech")
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now()
                        .plusSeconds(BigInteger.valueOf(3600).longValue()*
                                BigInteger.valueOf(24).intValue())))
                .signWith(getSignInKey(), SignatureAlgorithm.ES512)
                .compact();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), tokens);

    }

    private Key getSignInKey(){
        byte[] keyByte = Decoders.BASE64.decode(jwtUtil.getJwtSecretKey());
        return Keys.hmacShaKeyFor(keyByte);
    }
}
