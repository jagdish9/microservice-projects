package com.microservice.paymentservice.component;

import com.microservice.commonevents.event.OrderEvent;
import com.microservice.paymentservice.entity.ProcessedEvent;
import com.microservice.paymentservice.repository.ProcessedEventRepo;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentConsumer.class);

    @Autowired
    private ProcessedEventRepo repo;

    @KafkaListener(topics = "t-order-topic", groupId = "payment-group")
    public void consume(
            ConsumerRecord<String, OrderEvent> record,
            Acknowledgment acknowledgement
    ) {
        log.info("Received event: {}", record.value());
        String eventId = record.key();

        //idempotency check
        if(repo.existsById(eventId)) {
            acknowledgement.acknowledge();
            return;
        }

        try {
            log.info("Processing order: {}", eventId);

            //business logic
            processPayment(record.value());

            //save processed event
            repo.save(new ProcessedEvent(eventId));

            //manual commit
            acknowledgement.acknowledge();
        } catch (Exception e) {
            throw e; //handled by retry
        }
    }

    private void processPayment(OrderEvent event) {
        log.info("Payment processed successfully: {}", event.getOrderId());
    }
}
