package com.microservice.lockservice.dto;

import java.time.LocalDateTime;

public record ErrorResponse(String message, int statusCode, String path, LocalDateTime timestamp) {
}
