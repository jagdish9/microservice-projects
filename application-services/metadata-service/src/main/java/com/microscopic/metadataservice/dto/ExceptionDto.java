package com.microscopic.metadataservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionDto {
    private String message;
    private int statusCode;
    private LocalDateTime localDateTime;
}
