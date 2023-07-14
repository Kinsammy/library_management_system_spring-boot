//package com.sametech.library_management_system.config.security.service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//
//import java.util.function.Function;
//
//public class JwtService {
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public <T> T  extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder();
//    }
//}
