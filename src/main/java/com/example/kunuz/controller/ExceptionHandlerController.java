package com.example.kunuz.controller;

import com.example.kunuz.exp.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = new LinkedList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getDefaultMessage());
        }
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler({PhoneAlreadyRegisteredException.class})
    private ResponseEntity<?> handler(PhoneAlreadyRegisteredException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({ArgumentNullException.class})
    private ResponseEntity<?> handler(ArgumentNullException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({InCorrectPhoneNumberException.class})
    private ResponseEntity<?> handler(InCorrectPhoneNumberException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({IncorrectArgumentValueSizeException.class})
    private ResponseEntity<?> handler(IncorrectArgumentValueSizeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({LoginOrPasswordWrongException.class})
    private ResponseEntity<?> handler(LoginOrPasswordWrongException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({StatusBlockException.class})
    private ResponseEntity<?> handler(StatusBlockException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({ForbiddenException.class})
    private ResponseEntity<?> handler(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler({JwtNotValidException.class})
    private ResponseEntity<?> handler(JwtNotValidException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler({RegionNotFoundException.class})
    private ResponseEntity<?> handler(RegionNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({AttachNotFoundException.class})
    private ResponseEntity<?> handler(AttachNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({ArticleNotFoundException.class})
    private ResponseEntity<?> handler(ArticleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({ArticleNotPublishedException.class})
    private ResponseEntity<?> handler(ArticleNotPublishedException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({ArticleLikeAlreadyExists.class})
    private ResponseEntity<?> handler(ArticleLikeAlreadyExists e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({ArticleLikeNotFound.class})
    private ResponseEntity<?> handler(ArticleLikeNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({ArticleAlreadySaved.class})
    private ResponseEntity<?> handler(ArticleAlreadySaved e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({OnlyOwnerCaneUpdate.class})
    private ResponseEntity<?> handler(OnlyOwnerCaneUpdate e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({CouldNotRead.class})
    private ResponseEntity<?> handler(CouldNotRead e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({CommentLikeNotFound.class})
    private ResponseEntity<?> handler(CommentLikeNotFound e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({CommentLikeAlreadyExists.class})
    private ResponseEntity<?> handler(CommentLikeAlreadyExists e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }


}


