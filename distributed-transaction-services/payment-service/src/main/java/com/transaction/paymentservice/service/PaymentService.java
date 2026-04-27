package com.transaction.paymentservice.service;

import com.transaction.commonevents.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentProducer paymentProducer;

    @KafkaListener(topics = "tr-order-topic", groupId = "payment-service-group")
    public void processPayment(OrderEvent orderEvent) {
        if("CREATED".equals(orderEvent.getStatus())) {
            log.info("Processing payment for order: {}", orderEvent.getOrderId());
            boolean success = process(orderEvent);

            if(success) {
                orderEvent.setStatus("PAID");
            } else {
                orderEvent.setStatus("FAILED");
            }

            paymentProducer.sendPaymentEvent(orderEvent);
        }
    }

    private boolean process(OrderEvent orderEvent) {
        try {
            Thread.sleep(2000);
            log.info("Payment processed for order: {}", orderEvent.getOrderId());
            return true;
        } catch (InterruptedException e) {
            log.error("Payment processing interrupted for order: {}", orderEvent.getOrderId(), e);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @KafkaListener(topics = "tr-inventory-topic", groupId = "payment-service-group")
    public void refundPayment(OrderEvent orderEvent) {
        log.info("Refunding payment for order: {}", orderEvent.getOrderId());
        if("INVENTORY_FAILED".equals(orderEvent.getStatus())) {
            log.warn("Inventory failed for order: {}, skipping refund", orderEvent.getOrderId());
            orderEvent.setStatus("PAYMENT_REFUNDED");
            paymentProducer.sendPaymentEvent(orderEvent);
        }
    }
}
