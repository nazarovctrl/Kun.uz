package com.example.kunuz.exp;

public class JwtNotValidException extends RuntimeException {
    public JwtNotValidException(String message) {
        super(message);
    }
}
