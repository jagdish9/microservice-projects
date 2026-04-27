package com.microscopic.orderservice.service;

import com.microscopic.commonevents.events.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderService(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void createOrder(OrderCreatedEvent orderCreatedEvent) {
        kafkaTemplate.send("order-topic", orderCreatedEvent);
    }
}
