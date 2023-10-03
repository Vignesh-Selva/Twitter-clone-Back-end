package com.twitter.apis.exceptions;

public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException(String message) {
        super(message);
    }
}