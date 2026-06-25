package com.example.user.exception;

public class EmailAlreadyAvailableException extends RuntimeException {

    public EmailAlreadyAvailableException(String message) {
        super(message);
    }
}
