package com.example.productService.exception;

public class BadRequestResponseException extends RuntimeException{
    public BadRequestResponseException() {
    }

    public BadRequestResponseException(String message) {
        super(message);
    }
}
