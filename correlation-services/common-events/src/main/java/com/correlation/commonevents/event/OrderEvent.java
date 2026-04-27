package com.correlation.commonevents.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEvent {
    private String orderId;
    private String productId;
    private int quantity;
}
