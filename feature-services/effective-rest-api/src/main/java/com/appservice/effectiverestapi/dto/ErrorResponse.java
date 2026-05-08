package com.appservice.effectiverestapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private String message;
    private int errorCode;
    private LocalDateTime timestamp;
}
