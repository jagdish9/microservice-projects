package com.appservice.cqrsservice.command.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderCommand {
    private String productName;
    private int quantity;
}
