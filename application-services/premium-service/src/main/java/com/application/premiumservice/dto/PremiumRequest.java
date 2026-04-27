package com.application.premiumservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumRequest {

    @NotBlank
    private String orderId;

    @NotNull
    @Min(1)
    private Double amount;
}
