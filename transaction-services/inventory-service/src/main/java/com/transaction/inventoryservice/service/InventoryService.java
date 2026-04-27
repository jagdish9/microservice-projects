package com.transaction.inventoryservice.service;

import com.transaction.commonevents.events.InventoryEvent;
import com.transaction.commonevents.events.PaymentEvent;
import com.transaction.inventoryservice.entity.Inventory;
import com.transaction.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public void reserveEntity(PaymentEvent event) {
        try {
            Inventory inv = inventoryRepository.findById("product1")
                    .orElseThrow();

            if(inv.getStock() <= 0)
                throw new RuntimeException("Out of stock");

            inv.setStock(inv.getStock() - 1);

            inventoryRepository.save(inv);

            kafkaTemplate.send("inventory-topic",
                    new InventoryEvent(event.getOrderId(), "SUCCESS"));
            log.info("Creating topic with status success: inventory-topic");
        } catch (Exception ex) {
            kafkaTemplate.send("inventory-topic",
                    new InventoryEvent(event.getOrderId(), "FAILED"));
            log.info("Creating topic with status failed: inventory-topic");
        }
    }
}
