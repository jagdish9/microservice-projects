package com.retrymechanism.paymentservice.scheduler;

import com.retrymechanism.commonevents.dto.OrderDto;
import com.retrymechanism.commonevents.events.FailedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RetryScheduler {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private KafkaTemplate<String, OrderDto> kafkaTemplate;

    @Scheduled(fixedDelay = 60000) // every 1min
    public void retryFailedPayments() {
        Set<String> keys = redisTemplate.keys("FAILED_ORDER:*");

        for(String key : keys) {
            FailedEvent event = (FailedEvent) redisTemplate.opsForValue().get(key);

            try {
                kafkaTemplate.send("rt-order-topic", event.getPayload());
                redisTemplate.delete(key);
            } catch (Exception e) {
                event.setRetryCount(event.getRetryCount() + 1);
                redisTemplate.opsForValue().set(key, event);
            }
        }
    }
}
