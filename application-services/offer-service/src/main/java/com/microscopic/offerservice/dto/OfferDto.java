package com.microscopic.offerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OfferDto {
    private String offerName;
    private int quantity;
    private LocalDateTime orderDate;
}
