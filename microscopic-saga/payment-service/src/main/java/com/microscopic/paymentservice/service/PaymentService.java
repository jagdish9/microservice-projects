package com.microscopic.paymentservice.service;

import com.microscopic.events.CompensationEvent;
import com.microscopic.events.PaymentProcessedEvent;
import com.microscopic.events.PaymentRequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "payment-request-topic")
    public void processPayment(PaymentRequestEvent paymentRequestEvent) {
        boolean success = true;
        kafkaTemplate.send("payment-response-topic",
                new PaymentProcessedEvent(paymentRequestEvent.getOrderId(), success));
    }

    @KafkaListener(topics = "compensation-topic")
    public void rollbackPayment(CompensationEvent compensationEvent) {
      log.info("Refunding payment for - {}", compensationEvent.getOrderId());
    }
}
