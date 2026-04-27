package com.transaction.orderservice.service;

import com.transaction.commonevents.events.OrderCreatedEvent;
import com.transaction.orderservice.entity.Order;
import com.transaction.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public Order createOrder(Order order) {
        order.setStatus("CREATED");
        Order saved = orderRepository.save(order);

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        orderCreatedEvent.setOrderId(saved.getId());
        orderCreatedEvent.setQuantity(saved.getQuantity());
        orderCreatedEvent.setProductId(saved.getProductId());

        kafkaTemplate.send("order-topic", orderCreatedEvent);

        log.info("Created topic: order-topic");

        return saved;
    }

    public Order getCreatedOrder(Long id) {
        Optional<Order> existingOrder =  orderRepository.findById(id);
        return existingOrder.orElseThrow(() ->
                new RuntimeException("Order not found"));
    }
}
