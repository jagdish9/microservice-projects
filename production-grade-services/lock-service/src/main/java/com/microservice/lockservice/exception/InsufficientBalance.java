package com.microservice.lockservice.exception;

public class InsufficientBalance extends RuntimeException {
    public InsufficientBalance(String message) {
        super(message);
    }
}
