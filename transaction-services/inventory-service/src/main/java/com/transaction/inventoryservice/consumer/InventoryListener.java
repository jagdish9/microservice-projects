package com.transaction.inventoryservice.consumer;

import com.transaction.commonevents.events.PaymentEvent;
import com.transaction.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryListener {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "payment-topic")
    public void handlePaymentEvent(PaymentEvent event) {
        if(event.getStatus().equals("SUCCESS")) {
            inventoryService.reserveEntity(event);
        }
    }
}
