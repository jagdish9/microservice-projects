package com.appservice.orderservice.controller;

import com.appservice.orderservice.entity.Order;
import com.appservice.orderservice.service.OrderService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Order order,
                                         @RequestHeader("idempotency-key") String idempotencyKey) {
        log.info("Received request to create order: {}", order);
        return new ResponseEntity<>(orderService.createOrder(order, idempotencyKey), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable String id) {
        log.info("Received request to get order by ID: {}", id);
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }
}
