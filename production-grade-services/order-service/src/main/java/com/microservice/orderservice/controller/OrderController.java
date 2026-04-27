package com.microservice.orderservice.controller;

import com.microservice.orderservice.entity.Order;
import com.microservice.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Order order,
                                         @RequestHeader String idempotencyKey) {
        return new ResponseEntity<>(orderService.createOrder(order, idempotencyKey), HttpStatus.CREATED);
    }
}
