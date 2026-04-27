package com.appservice.orderservice.service;

import com.appservice.commonevents.events.OrderEvent;
import com.appservice.orderservice.constants.OrderStatus;
import com.appservice.orderservice.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.appservice.orderservice.repository.OrderRepository;
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
    @CacheEvict(value = "orders", key = "#result.id")
    public Order createOrder(Order order, String idempotencyKey) {
        Order savedOrder = orderRepository.findByIdempotencyKey(idempotencyKey)
                .orElseGet(() -> {
                    log.info("Creating new order with idempotency key: {}", order.getIdempotencyKey());
                    Order newOrder = new Order();
                    newOrder.setIdempotencyKey(idempotencyKey);
                    newOrder.setProductName(order.getProductName());
                    newOrder.setQuantity(order.getQuantity());
                    newOrder.setCreatedAt(LocalDateTime.now());
                    newOrder.setStatus(OrderStatus.CREATED.name());
                    return orderRepository.save(newOrder);
                });

        log.info("Order created with ID: {}", savedOrder.getId());
        OrderEvent event = new OrderEvent(savedOrder.getId(), savedOrder.getStatus());
        sendOrder(event);
        return savedOrder;
    }

    @Transactional("kafkaTransactionManager")
    private void sendOrder(OrderEvent event) {
        kafkaTemplate.executeInTransaction(kt -> {
            log.info("Sending order event for order ID: {}", event.getOrderId());
            kt.send("t-app-order-topic", event.getOrderId(), event);
            return true;
        });
    }

    @Cacheable(value = "orders", key = "#id")
    public Order getOrderById(String id) {
        log.info("Fetching order from DB with ID: {}", id);
        simulateSlowCall();
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    private void simulateSlowCall() {
        log.info("Simulating slow database call...");
        try {
            Thread.sleep(3000); // simulate DB delay
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
