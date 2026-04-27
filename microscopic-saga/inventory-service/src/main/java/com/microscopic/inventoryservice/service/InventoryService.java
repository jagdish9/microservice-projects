package com.microscopic.inventoryservice.service;

import com.microscopic.events.CompensationEvent;
import com.microscopic.events.InventoryRequestEvent;
import com.microscopic.events.InventoryReservedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "inventory-request-topic")
    public void reserve(InventoryRequestEvent inventoryRequestEvent) {
        boolean reserved = true;

        kafkaTemplate.send("inventory-response-topic",
                new InventoryReservedEvent(inventoryRequestEvent.getOrderId(), reserved));
    }

    @KafkaListener(topics = "compensation-topic")
    public void releaseInventory(CompensationEvent compensationEvent) {
        log.info("Releasing inventory for - {}", compensationEvent.getOrderId());
    }

}
