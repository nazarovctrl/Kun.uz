package com.example.kunuz.exp;

public class InCorrectPhoneNumberException extends RuntimeException {
    public InCorrectPhoneNumberException(String message) {
        super(message);
    }
}
