package com.microservice.orderservice.service;

import com.microservice.commonevents.event.OrderEvent;
import com.microservice.orderservice.entity.Order;
import com.microservice.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderService(OrderRepository orderRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional("transactionManager")
    public Order createOrder(Order order, String idempotencyKey) {
        Order createdOrder = orderRepository.findByIdempotencyKey(idempotencyKey)
                .orElseGet(() -> {
                    Order existingOrder = Order.builder()
                            .id(order.getId())
                            .itemName(order.getItemName())
                            .quantity(order.getQuantity())
                            .idempotencyKey(idempotencyKey)
                            .createdAt(LocalDateTime.now())
                            .status("CREATED")
                            .build();
                    return orderRepository.save(existingOrder);
                });

        OrderEvent event = new OrderEvent(createdOrder.getId(), "CREATED");
        sendOrder(event);
        return createdOrder;
    }

    @Transactional("kafkaTransactionManager")
    public void sendOrder(OrderEvent event) {
        kafkaTemplate.executeInTransaction(kt -> {
            log.info("Sending events: {}", event.getOrderId());
            kt.send("t-order-topic", event.getOrderId(), event);
            return true;
        });
    }
}
