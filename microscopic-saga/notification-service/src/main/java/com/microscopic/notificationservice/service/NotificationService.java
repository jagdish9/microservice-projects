package com.microscopic.notificationservice.service;

import com.microscopic.events.InventoryReservedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    @KafkaListener(topics = "order-completed-topic")
    public void notify(InventoryReservedEvent inventoryReservedEvent) {
        log.info("Order completed, Notification sent");
    }
}
