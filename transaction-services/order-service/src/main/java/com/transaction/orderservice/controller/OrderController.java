package com.transaction.orderservice.controller;

import com.transaction.orderservice.entity.Order;
import com.transaction.orderservice.service.OrderService;;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order createdOrder = orderService.createOrder(order);
        log.info("Created order successfully - {}", createdOrder.getProductId());
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getCreatedOrder(Long id) {
        log.info("Getting order details by id - {}", id);
        return ResponseEntity.ok(orderService.getCreatedOrder(id));
    }
}
