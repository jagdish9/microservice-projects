package com.appservice.cqrsservice.query.service;

import com.appservice.cqrsservice.entity.Order;
import com.appservice.cqrsservice.query.dto.OrderResponse;
import com.appservice.cqrsservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public OrderQueryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderResponse getOrder(Long id) {
        Order order =  orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return new OrderResponse(
                order.getId(),
                order.getProductName(),
                order.getQuantity()
        );
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getProductName(),
                        order.getQuantity()
                )
                )
                .collect(Collectors.toList());
    }
}
