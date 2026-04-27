package com.application.premiumservice.exception;

public class DuplicatePremiumException extends RuntimeException {
    public DuplicatePremiumException(String message) {
        super(message);
    }
}
