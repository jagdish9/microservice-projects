package com.microservice.lockservice.exception;

import com.microservice.lockservice.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(InsufficientBalance.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ErrorResponse insufficientBalance(InsufficientBalance exception, HttpServletRequest request) {
        return new ErrorResponse(
                exception.getMessage(),
                HttpStatus.PAYMENT_REQUIRED.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse genericException(Exception exception, HttpServletRequest request) {
        return new ErrorResponse(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI(),
                LocalDateTime.now()
        );
    }
}
