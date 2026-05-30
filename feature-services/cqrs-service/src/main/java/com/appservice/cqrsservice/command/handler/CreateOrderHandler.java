package com.appservice.cqrsservice.command.handler;

import com.appservice.cqrsservice.command.dto.CreateOrderCommand;
import com.appservice.cqrsservice.command.service.OrderCommandService;
import com.appservice.cqrsservice.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderHandler {

    private final OrderCommandService orderCommandService;

    public CreateOrderHandler(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    public Order handle(CreateOrderCommand command) {
        return orderCommandService.createOrder(command);
    }
}
