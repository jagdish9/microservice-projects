package com.uiapp.userservice.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorResponse {

    private String message;
    private int status;
    private LocalDateTime localDateTime;
}
