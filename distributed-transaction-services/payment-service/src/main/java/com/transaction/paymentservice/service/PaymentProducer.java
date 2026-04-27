package com.transaction.paymentservice.service;

import com.transaction.commonevents.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentProducer {

    private static final Logger log = LoggerFactory.getLogger(PaymentProducer.class);

    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public PaymentProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentEvent(OrderEvent orderEvent) {
        log.info("Sending payment event for order: {}", orderEvent.getOrderId());
        kafkaTemplate.send("tr-payment-topic", orderEvent);
        log.info("Sent payment event: {}", orderEvent);
    }
}
