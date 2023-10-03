package com.twitter.apis.exceptions;

public class UserOrTweetNotFoundException extends RuntimeException {
    public UserOrTweetNotFoundException(String message) {
        super(message);
    }
}
