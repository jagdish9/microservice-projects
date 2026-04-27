package com.retrymechanism.paymentservice.service;

import com.retrymechanism.commonevents.dto.OrderDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentConsumer {

    @KafkaListener(topics = "rt-order-topic", groupId = "rt-payment-group")
    @Transactional
    public void consume(OrderDto dto) {
        try {
            if(dto.getPrice() > 5000) {
                throw new RuntimeException("Payment failed");
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
