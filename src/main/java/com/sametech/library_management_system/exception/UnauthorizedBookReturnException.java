package com.sametech.library_management_system.exception;

public class UnauthorizedBookReturnException extends RuntimeException {
    public UnauthorizedBookReturnException(String message) {
        super(message);
    }
}
