package com.twitter.apis.exceptions;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(String message) {
        super(message);
    }
}
