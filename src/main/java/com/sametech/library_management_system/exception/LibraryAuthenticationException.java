package com.sametech.library_management_system.exception;

import org.springframework.security.core.AuthenticationException;

public class LibraryAuthenticationException extends AuthenticationException {
    public LibraryAuthenticationException(String message) {
        super(message);
    }
}
