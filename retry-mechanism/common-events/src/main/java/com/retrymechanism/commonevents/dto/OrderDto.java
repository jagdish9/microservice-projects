package com.retrymechanism.commonevents.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    private String product;
    private int quantity;
    private double price;
}
