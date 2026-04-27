package com.transaction.paymentservice.service;

import com.transaction.commonevents.events.OrderCreatedEvent;
import com.transaction.commonevents.events.PaymentEvent;
import com.transaction.paymentservice.entity.Payment;
import com.transaction.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public void processPayment(OrderCreatedEvent event) {
        Payment payment = new Payment();
        payment.setOrderId(event.getOrderId());

        try {
            payment.setStatus("SUCCESS");

            paymentRepository.save(payment);

            kafkaTemplate.send("payment-topic",
                    new PaymentEvent(event.getOrderId(), "SUCCESS"));
            log.info("Creating topic with status success: payment-topic");
        } catch(Exception e) {
            payment.setStatus("FAILED");

            paymentRepository.save(payment);

            kafkaTemplate.send("payment-topic",
                    new PaymentEvent(event.getOrderId(), "FAILED"));
            log.info("Creating topic with status failed: payment-topic");
        }
    }


    public Payment findByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }
}
