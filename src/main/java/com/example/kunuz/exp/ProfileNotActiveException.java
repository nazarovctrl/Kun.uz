package com.example.kunuz.exp;

public class ProfileNotActiveException extends RuntimeException {
    public ProfileNotActiveException(String message) {
        super(message);
    }
}
