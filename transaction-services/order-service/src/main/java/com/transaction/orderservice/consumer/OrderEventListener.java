package com.transaction.orderservice.consumer;

import com.transaction.commonevents.events.InventoryEvent;
import com.transaction.orderservice.entity.Order;
import com.transaction.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "inventory-topic")
    public void handleInventoryEvent(InventoryEvent event) {
        log.info("Received topic:: inventory-topic");
        if(event.getStatus().equals("FAILED")) {
            Order order = orderRepository.findById(event.getOrderId())
                    .orElseThrow();

            order.setStatus("CANCELLED");

            orderRepository.save(order);
        }
    }
}
