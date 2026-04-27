package com.application.premiumservice.exception;

public class PremiumNotFoundException extends RuntimeException {
    public PremiumNotFoundException(String message) {
        super(message);
    }
}
