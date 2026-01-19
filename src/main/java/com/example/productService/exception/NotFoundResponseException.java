package com.example.productService.exception;

public class NotFoundResponseException extends RuntimeException{
    public NotFoundResponseException() {
    }

    public NotFoundResponseException(String message) {
        super(message);
    }
}
