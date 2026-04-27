package com.microservice.adviceservice.service;

import com.microservice.events.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AdviceConsumerService {

    @KafkaListener(topics = "command-topic", groupId = "advice-group")
    public void consume(OrderCreatedEvent event) {
        System.out.println("Sending mail to: "+ event.getEmail());
    }
}
