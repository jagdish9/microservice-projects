package com.microservice.commandservice.controller;

import com.microservice.commandservice.service.OrderProducerService;
import com.microservice.events.OrderCreatedEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderProducerService orderProducerService;

    public OrderController(OrderProducerService orderProducerService) {
        this.orderProducerService = orderProducerService;
    }

    @PostMapping("/submit")
    public String createOrder(@RequestBody OrderCreatedEvent event) {
        orderProducerService.publishOrderEvent(event);

        return "Order placed successfully";
    }
}
