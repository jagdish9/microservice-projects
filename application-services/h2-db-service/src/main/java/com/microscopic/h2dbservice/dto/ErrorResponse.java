package com.microscopic.h2dbservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private int statusCode;
    private LocalDateTime timeStamp;

    public ErrorResponse() {}
}
