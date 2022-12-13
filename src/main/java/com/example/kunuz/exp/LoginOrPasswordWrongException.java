package com.example.kunuz.exp;

public class LoginOrPasswordWrongException extends RuntimeException {
    public LoginOrPasswordWrongException(String message) {
        super(message);
    }
}
