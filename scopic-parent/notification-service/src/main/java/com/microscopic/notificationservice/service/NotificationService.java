package com.microscopic.notificationservice.service;

import com.microscopic.commonevents.events.InventoryReservedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    @KafkaListener(topics = "inventory-topic")
    public void sendNotification(InventoryReservedEvent event) {
        if(event.isReserved()) {
            log.info("Sending notification for Order: {}", event.getOrderId());
        }
    }
}
