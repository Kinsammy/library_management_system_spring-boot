package com.sametech.library_management_system.service.serviceImplementation;

import com.sametech.library_management_system.config.security.service.JwtService;
import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.models.token.Token;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import com.sametech.library_management_system.data.repository.TokenRepository;
import com.sametech.library_management_system.exception.LibraryLogicException;
import com.sametech.library_management_system.service.serviceInterface.ITokenService;
import com.sametech.library_management_system.util.AppUtilities;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService implements ITokenService {
    private final TokenRepository tokenRepository;
    private final EntityManager entityManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public String generateAndSaveToken(AppUser appUser) {
        Optional<Token> existingToken =  tokenRepository.findTokenByAppUser(appUser);
        existingToken.ifPresent(tokenRepository::delete);
        String generateToken = jwtService.generateToken(appUser);
        Token token = Token.builder()
                .appUser(entityManager.merge(appUser))
                .token(generateToken)
                .build();
        tokenRepository.save(token);
        return generateToken;
    }





    @Override
    public Optional<Token> validateReceivedToken(AppUser appUser, String token) {
        Optional<Token> receivedToken = tokenRepository.findTokenByAppUserAndToken(appUser, token);
        if (receivedToken.isEmpty()) throw new LibraryLogicException("Invalid token");
        else if (receivedToken.get().getExpiryTime().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(receivedToken.get());
            throw new LibraryLogicException("Token us expired");
        }

        return receivedToken;
    }

    @Override
    public void deleteToken(Token token) {
        tokenRepository.delete(token);
    }



    @Override
    public void saveLibraryToken(AppUser appUser, String token) {
        var verifiedToken = new Token(appUser, token);
        tokenRepository.save(verifiedToken);
    }
}
