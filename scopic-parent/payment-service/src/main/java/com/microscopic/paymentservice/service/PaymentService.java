package com.microscopic.paymentservice.service;

import com.microscopic.commonevents.events.OrderCreatedEvent;
import com.microscopic.commonevents.events.PaymentProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {

    private final KafkaTemplate<String, PaymentProcessedEvent> kafkaTemplate;

    public PaymentService(KafkaTemplate<String, PaymentProcessedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order-topic", groupId = "payment-group")
    public void processPayment(OrderCreatedEvent event) {
        log.info("Processing payment for - {}", event.getOrderId());

        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent();
        paymentProcessedEvent.setOrderId(event.getOrderId());
        paymentProcessedEvent.setSuccess(true);

        kafkaTemplate.send("payment-topic", paymentProcessedEvent);
    }
}
