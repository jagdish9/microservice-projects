package com.appservice.cqrsservice.command.service;

import com.appservice.cqrsservice.command.dto.CreateOrderCommand;
import com.appservice.cqrsservice.entity.Order;
import com.appservice.cqrsservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderCommandService {

    private final OrderRepository orderRepository;

    public OrderCommandService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(CreateOrderCommand command) {
        Order order = new Order();
        order.setQuantity(command.getQuantity());
        order.setProductName(command.getProductName());
        return orderRepository.save(order);
    }
}
