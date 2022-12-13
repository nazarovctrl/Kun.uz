package com.example.kunuz.exp;

public class ArticleNotPublishedException extends RuntimeException {
    public ArticleNotPublishedException(String message) {
        super(message);
    }
}
