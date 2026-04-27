package com.retrymechanism.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/retry")
public class RetryController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/{id}")
    public String retry(@PathVariable Long id) {
        String key = "FAILED_ORDER:" + id;

        Object event = redisTemplate.opsForValue().get(key);

        if(event == null) return "Not found";

        kafkaTemplate.send("rt-order-topic", event);

        redisTemplate.delete(key);

        return "Retried";
    }
}
