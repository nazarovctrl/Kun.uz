package com.example.kunuz.exp;

public class MailAlreadyExists extends RuntimeException {
    public MailAlreadyExists(String message) {
        super(message);
    }
}
