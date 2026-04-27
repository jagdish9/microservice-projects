package com.appservice.paymentservice.service;

import com.appservice.commonevents.events.OrderEvent;
import com.appservice.paymentservice.entity.ProcessedEvents;
import com.appservice.paymentservice.repository.PaymentRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RedisLockService redisLockService; // For distributed locking if needed

    @KafkaListener(topics = "t-app-order-topic", groupId = "app-payment-group")
    public void consume(ConsumerRecord<String, OrderEvent> record,
                        Acknowledgment ack) {

        // Distributed lock example (optional, depending on your use case)
        String paymentId = record.value().getOrderId();
        String lockKey = "lock:payment:" + paymentId;

        boolean lockAcquired = redisLockService.acquireLock(lockKey, 30); // Acquire lock for 30 seconds
        if (!lockAcquired) {
            log.warn("Another instance is running for payment ID: {}. Skipping processing.", paymentId);
            return;
        }
        // Distributed lock example end
        // [If we are using idempotency check, we can skip distributed lock as it will handle duplicates]
        // Redis/Redisson locking ensures safe execution across distributed systems

        // Simulate payment processing logic
        log.info("Processing payment for event: {}", record.value());

        String eventId = record.key();

        log.info("Received event with ID: {}", eventId);

        //idempotency check
        if(paymentRepository.existsById(eventId)) {
            log.info("Event with ID {} already processed. Skipping.", eventId);
            ack.acknowledge();
            return;
        }

        try {
            log.info("Processing payment for event ID: {}", eventId);

            //business logic
            processPayment(record.value());

            //save processed event
            paymentRepository.save(new ProcessedEvents(eventId));

            //manual commit
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error processing payment for event ID: {}", eventId, e);
            throw e; // Let retry mechanism handle it
        }
    }

    private void processPayment(OrderEvent event) {
        // Simulate payment processing logic (e.g., call to payment gateway)
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            log.error("Payment processing interrupted for order ID: {}", event.getOrderId(), e);
        }
        log.info("Payment processed successfully for order ID: {}", event.getOrderId());
    }
}
