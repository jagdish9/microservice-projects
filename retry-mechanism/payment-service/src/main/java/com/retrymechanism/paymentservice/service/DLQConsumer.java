package com.retrymechanism.paymentservice.service;

import com.retrymechanism.paymentservice.constants.PaymentStatus;
import com.retrymechanism.commonevents.dto.OrderDto;
import com.retrymechanism.commonevents.events.FailedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DLQConsumer {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @KafkaListener(topics = "order-dlq", groupId = "dlq-group")
    public void consume(OrderDto dto) {
        String id = UUID.randomUUID().toString();

        FailedEvent event = new FailedEvent();
        event.setEventId(id);
        event.setPayload(dto);
        event.setRetryCount(0);
        event.setStatus(PaymentStatus.FAILED.name());
        event.setCreatedAt(LocalDateTime.now());

        redisTemplate.opsForValue().set("FAILED_ORDER:" + id, event);
    }
}
