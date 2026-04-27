package com.transaction.inventoryservice.service;

import com.transaction.commonevents.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryProducer {

    private static final Logger log = LoggerFactory.getLogger(InventoryProducer.class);

    private final InventoryService inventoryService;

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public InventoryProducer(InventoryService inventoryService, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.inventoryService = inventoryService;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendInventoryEvent(OrderEvent orderEvent) {
        log.info("Sending inventory event for order: {}", orderEvent.getOrderId());
        kafkaTemplate.send("tr-inventory-topic", orderEvent);
    }
}
