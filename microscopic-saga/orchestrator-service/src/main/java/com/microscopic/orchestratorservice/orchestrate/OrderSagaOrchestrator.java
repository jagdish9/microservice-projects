package com.microscopic.orchestratorservice.orchestrate;

import com.microscopic.events.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderSagaOrchestrator {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order-created-topic", containerFactory = "orderCreatedKafkaListenerContainerFactory")
    public void handleOrderCreated(OrderCreatedEvent orderCreatedEvent) {
        PaymentRequestEvent paymentRequestEvent = new PaymentRequestEvent();
        paymentRequestEvent.setOrderId(orderCreatedEvent.getOrderId());

        kafkaTemplate.send("payment-request-topic", paymentRequestEvent);
    }

    @KafkaListener(topics = "payment-response-topic", containerFactory = "paymentProcessedKafkaListenerContainerFactory")
    public void handlePaymentResponse(PaymentProcessedEvent paymentProcessedEvent) {
        if(paymentProcessedEvent.isSuccess()) {
            InventoryRequestEvent inventoryRequestEvent = new InventoryRequestEvent();
            inventoryRequestEvent.setOrderId(paymentProcessedEvent.getOrderId());

            kafkaTemplate.send("inventory-request-topic", inventoryRequestEvent);
        } else {
            kafkaTemplate.send("compensation-topic",
                    new CompensationEvent(paymentProcessedEvent.getOrderId(), "Payment Failed"));
        }
    }

    @KafkaListener(topics = "inventory-response-topic", containerFactory = "inventoryReservedKafkaListenerContainerFactory")
    public void handleInventoryResponse(InventoryReservedEvent inventoryReservedEvent) {
        if(inventoryReservedEvent.isReserved()) {
            kafkaTemplate.send("order-completed-topic", inventoryReservedEvent);
        } else {
            kafkaTemplate.send("compensation-topic",
                    new CompensationEvent(inventoryReservedEvent.getOrderId(), "Inventory Failed"));
        }
    }
}
