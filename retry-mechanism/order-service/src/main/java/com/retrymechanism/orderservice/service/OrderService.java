package com.retrymechanism.orderservice.service;

import com.retrymechanism.commonevents.dto.OrderDto;
import com.retrymechanism.orderservice.entity.Order;
import com.retrymechanism.orderservice.exception.OrderNotFoundException;
import com.retrymechanism.orderservice.mapper.OrderMapper;
import com.retrymechanism.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, OrderDto> kafkaTemplate;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Order createOrder(OrderDto dto) {

        Order saved = orderRepository.save(OrderMapper.toEntity(dto));

        try {
            kafkaTemplate.send("rt-order-topic", dto);
        } catch (Exception e) {
            throw new RuntimeException("Kafka send failed", e);
        }

        return saved;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Order get(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Order update(OrderDto dto, Long id) {
        Order existingOrder = get(id);

        existingOrder.setQuantity(dto.getQuantity());
        existingOrder.setPrice(dto.getPrice());
        existingOrder.setProduct(dto.getProduct());

        return orderRepository.save(existingOrder);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Order patch(double price, Long id) {
        Order existingOrder = get(id);

        existingOrder.setPrice(price);

        return orderRepository.save(existingOrder);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String deleteById(Long id) {
        orderRepository.deleteById(id);
        String deleteMessage = "OrderId deleted successfully";
        log.info("{} - {}", deleteMessage, id);
        return deleteMessage + " - " + id;
    }
}
