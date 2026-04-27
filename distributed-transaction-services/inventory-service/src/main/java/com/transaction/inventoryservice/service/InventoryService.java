package com.transaction.inventoryservice.service;

import com.transaction.commonevents.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    private InventoryProducer inventoryProducer;

    @KafkaListener(topics = "tr-payment-topic", groupId = "inventory-service-group")
    public void processInventory(OrderEvent orderEvent) {
        if("PAID".equals(orderEvent.getStatus())) {
            log.info("Processing inventory for order: {}", orderEvent.getOrderId());
            boolean available = checkStock(orderEvent);

            if(available) {
                orderEvent.setStatus("COMPLETED");
            } else {
                log.warn("Inventory processing failed for order: {}, sending failure event", orderEvent.getOrderId());
                orderEvent.setStatus("INVENTORY_FAILED");
            }

            inventoryProducer.sendInventoryEvent(orderEvent);
        }
    }

    private boolean checkStock(OrderEvent orderEvent) {
        try {
            Thread.sleep(1000);
            log.info("Stock checked for order: {}", orderEvent.getOrderId());
            return true; // Simulate stock availability
        } catch (InterruptedException e) {
            log.error("Stock check interrupted for order: {}", orderEvent.getOrderId(), e);
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
