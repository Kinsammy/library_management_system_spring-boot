package com.sametech.library_management_system.service.serviceInterface;

import com.sametech.library_management_system.data.models.token.Token;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.data.models.users.LibraryUser;

import java.util.Optional;

public interface ITokenService {
    String generateAndSaveToken(AppUser appUser);
    Optional<Token> validateReceivedToken(AppUser appUser, String token);
    void deleteToken(Token token);
    void saveLibraryToken(AppUser appUser, String token);
}
