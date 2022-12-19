package com.example.kunuz.exp;

public class CommentLikeAlreadyExists extends RuntimeException {
    public CommentLikeAlreadyExists(String message) {
        super(message);
    }
}
