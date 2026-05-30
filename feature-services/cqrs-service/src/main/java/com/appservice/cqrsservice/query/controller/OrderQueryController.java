package com.appservice.cqrsservice.query.controller;

import com.appservice.cqrsservice.query.dto.OrderResponse;
import com.appservice.cqrsservice.query.handler.GetOrderHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/queries/orders")
public class OrderQueryController {

    private final GetOrderHandler orderHandler;

    public OrderQueryController(GetOrderHandler orderHandler) {
        this.orderHandler = orderHandler;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse getOrder(@PathVariable Long id) {
        return orderHandler.getOrder(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders() {
        return orderHandler.getAllOrders();
    }
}
