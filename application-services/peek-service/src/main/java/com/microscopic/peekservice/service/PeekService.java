package com.microscopic.peekservice.service;

import com.microscopic.peekservice.exception.PeekFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class PeekService {

    private static final Logger log = LoggerFactory.getLogger(PeekService.class);

    @KafkaListener(topics = "offer-topic", groupId = "peek-group")
    public void consume(String message, Acknowledgment acknowledgement) {
        try {
            log.info("Received message: {}", message);

            //simulate business logic
            doPayment(message);

            //commit offset only after success
            acknowledgement.acknowledge();

            log.info("Offset committed successfully");
        } catch (Exception e) {
            log.error("Error occurred: {}", e.getMessage());

            //Do not acknowledge, message will be retried
        }
    }

    private void doPayment(String message) {
        if(message.contains("fail")) {
            throw new PeekFailedException("Peek failed");
        }
        log.info("Peeked offer successfully for: {}", message);
    }
}
