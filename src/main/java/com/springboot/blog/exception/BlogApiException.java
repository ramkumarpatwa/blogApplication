package com.springboot.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BlogApiException extends RuntimeException {//we will throw this exception whenever we write some business logic or validate request paramenter
    private HttpStatus httpStatus;
    private String message;

    public BlogApiException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public BlogApiException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }
}
