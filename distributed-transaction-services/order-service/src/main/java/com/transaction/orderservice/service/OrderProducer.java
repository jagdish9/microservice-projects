package com.transaction.orderservice.service;

import com.transaction.commonevents.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderProducer.class);

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(OrderEvent orderEvent) {
        log.info("Sending order event for order: {}", orderEvent.getOrderId());
        kafkaTemplate.send("tr-order-topic", orderEvent);
        log.info("Sent order event: {}", orderEvent);
    }
}
