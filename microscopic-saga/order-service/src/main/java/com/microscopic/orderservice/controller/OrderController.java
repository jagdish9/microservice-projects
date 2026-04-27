package com.microscopic.orderservice.controller;

import com.microscopic.events.OrderCreatedEvent;
import com.microscopic.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestBody OrderCreatedEvent event) {
        String orderId = event.getOrderId();
        orderService.createOrder(event);
        String orderMsg = orderId + " created successfully";
        log.info(orderMsg);
        return orderMsg;
    }
}
