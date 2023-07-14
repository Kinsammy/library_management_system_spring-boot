package com.sametech.library_management_system.service.serviceImplementation;

import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.TokenResponse;
import com.sametech.library_management_system.data.models.token.Token;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import com.sametech.library_management_system.data.repository.TokenRepository;
import com.sametech.library_management_system.service.serviceInterface.ITokenService;
import com.sametech.library_management_system.util.AppUtilities;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService implements ITokenService {
    private final TokenRepository tokenRepository;

    @Override
    public TokenResponse generateAndSaveToken(LibraryUser libraryUser) {
        Optional<Token> existingToken =  tokenRepository.findTokenByLibraryUser(libraryUser);
        existingToken.ifPresent(tokenRepository::delete);
        String generateToken = AppUtilities.generateRandomString(64);
        Token token = Token.builder()
                .libraryUser(libraryUser)
                .token(generateToken)
                .build();
        tokenRepository.save(token);
        return TokenResponse.builder()
                .generatedToken(generateToken)
                .build();
    }

    @Override
    public Optional<Token> validateReceivedToken(LibraryUser libraryUser, String token) {
        return Optional.empty();
    }

    @Override
    public ApiResponse deleteToken(Token token) {
        return null;
    }

    @Override
    public void saveLibraryToken(LibraryUser libraryUser, String token) {

    }
}
