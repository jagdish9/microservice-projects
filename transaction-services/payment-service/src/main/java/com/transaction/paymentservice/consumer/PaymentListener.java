package com.transaction.paymentservice.consumer;

import com.transaction.commonevents.events.InventoryEvent;
import com.transaction.commonevents.events.OrderCreatedEvent;
import com.transaction.commonevents.events.PaymentEvent;
import com.transaction.paymentservice.entity.Payment;
import com.transaction.paymentservice.repository.PaymentRepository;
import com.transaction.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentListener {

    private final PaymentService paymentService;

    @KafkaListener(topics = "order-topic")
    public void handleOrderEvent(OrderCreatedEvent event) {
        log.info("Handle order");
        log.info("Received topic: order-topic");
        paymentService.processPayment(event);
    }

    @KafkaListener(topics = "inventory-topic")
    public void refundPayment(InventoryEvent event) {
        log.info("Refund payment");
        log.info("Received topic: inventory-topic");
        if (event.getStatus().equals("FAILED")) {
            Payment payment =
                    paymentService.findByOrderId(event.getOrderId());

            payment.setStatus("REFUNDED");
            paymentService.save(payment);
        }
    }
}
