package com.appservice.effectiverestapi.exception;

import com.appservice.effectiverestapi.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;

@ControllerAdvice
@ResponseBody
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFoundException(ResourceNotFoundException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .errorCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ErrorResponse handleMaxSizeException(MaxUploadSizeExceededException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .errorCode(HttpStatus.PAYLOAD_TOO_LARGE.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRuntime(RuntimeException exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse genericException(Exception exception) {
        return ErrorResponse.builder()
                .message(exception.getMessage())
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
