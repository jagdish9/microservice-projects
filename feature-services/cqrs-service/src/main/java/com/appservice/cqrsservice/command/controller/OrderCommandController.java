package com.appservice.cqrsservice.command.controller;

import com.appservice.cqrsservice.command.dto.CreateOrderCommand;
import com.appservice.cqrsservice.command.handler.CreateOrderHandler;
import com.appservice.cqrsservice.entity.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commands/orders")
public class OrderCommandController {

    private final CreateOrderHandler orderHandler;

    public OrderCommandController(CreateOrderHandler orderHandler) {
        this.orderHandler = orderHandler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody CreateOrderCommand orderCommand) {
        return orderHandler.handle(orderCommand);
    }
}
