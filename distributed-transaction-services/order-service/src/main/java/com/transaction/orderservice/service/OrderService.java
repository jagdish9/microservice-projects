package com.transaction.orderservice.service;

import com.transaction.commonevents.OrderEvent;
import com.transaction.orderservice.constants.OrderStatus;
import com.transaction.orderservice.entity.Order;
import com.transaction.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    @Autowired
    private OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.CREATED.name());
        Order createdOrder = orderRepository.save(order);

        log.info("Created order: {}", createdOrder.getId());

        orderProducer.sendOrderEvent(new OrderEvent(createdOrder.getId(), createdOrder.getStatus(),
                createdOrder.getAmount(), createdOrder.getItemName(), createdOrder.getQuantity()));
        return createdOrder;
    }

    @KafkaListener(topics = "tr-payment-topic", groupId = "order-service-group")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        if("PAYMENT_REFUNDED".equals(orderEvent.getStatus())) {
            log.info("Received payment refunded event for order: {}", orderEvent.getOrderId());
            cancelOrder(orderEvent.getOrderId());
        }
    }

    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(OrderStatus.CANCELLED.name());
            orderRepository.save(order);
            log.info("Cancelled order: {}", orderId);
        } else {
            log.warn("Order not found for cancellation: {}", orderId);
        }
    }
}
