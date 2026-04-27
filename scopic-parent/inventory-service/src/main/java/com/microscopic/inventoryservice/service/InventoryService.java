package com.microscopic.inventoryservice.service;

import com.microscopic.commonevents.events.InventoryReservedEvent;
import com.microscopic.commonevents.events.PaymentProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryService {

    private final KafkaTemplate<String, InventoryReservedEvent> kafkaTemplate;

    public InventoryService(KafkaTemplate<String, InventoryReservedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "payment-topic")
    public void reserveInventory(PaymentProcessedEvent event) {
        if(event.isSuccess()) {
            InventoryReservedEvent inventoryReservedEvent = new InventoryReservedEvent();
            inventoryReservedEvent.setOrderId(event.getOrderId());
            inventoryReservedEvent.setReserved(true);

            log.info("Order is reserved at inventory - {}", event.getOrderId());

            kafkaTemplate.send("inventory-topic", inventoryReservedEvent);
        }
    }
}
