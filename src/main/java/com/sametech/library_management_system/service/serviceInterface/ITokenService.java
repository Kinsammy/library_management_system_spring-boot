package com.sametech.library_management_system.service.serviceInterface;

import com.sametech.library_management_system.data.dto.response.ApiResponse;
import com.sametech.library_management_system.data.dto.response.TokenResponse;
import com.sametech.library_management_system.data.models.token.Token;
import com.sametech.library_management_system.data.models.users.LibraryUser;

import java.util.Optional;

public interface ITokenService {
    TokenResponse generateAndSaveToken(LibraryUser libraryUser);
    Optional<Token> validateReceivedToken(LibraryUser libraryUser, String token);
    ApiResponse deleteToken(Token token);
    void saveLibraryToken(LibraryUser libraryUser, String token);
}
