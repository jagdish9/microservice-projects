package com.retrymechanism.orderservice.mapper;

import com.retrymechanism.commonevents.dto.OrderDto;
import com.retrymechanism.orderservice.entity.Order;

public class OrderMapper {

    public static Order toEntity(OrderDto dto) {
        Order order = new Order();
        order.setPrice(dto.getPrice());
        order.setProduct(dto.getProduct());
        order.setQuantity(dto.getQuantity());
        return order;
    }
}
