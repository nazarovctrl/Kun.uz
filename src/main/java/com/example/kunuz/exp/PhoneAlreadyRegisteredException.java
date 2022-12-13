package com.example.kunuz.exp;

public class PhoneAlreadyRegisteredException extends RuntimeException {
    public PhoneAlreadyRegisteredException(String message) {
        super(message);
    }

}
