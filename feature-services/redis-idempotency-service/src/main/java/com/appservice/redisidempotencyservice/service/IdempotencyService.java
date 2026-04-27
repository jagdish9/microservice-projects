package com.appservice.redisidempotencyservice.service;

import com.appservice.redisidempotencyservice.dto.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class IdempotencyService {

    private static final Logger log = LoggerFactory.getLogger(IdempotencyService.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final long TTL = 10;

    public boolean isDuplicate(String key) {
        log.info("Checking key in redis cache");
        return redisTemplate.hasKey(key);
    }

    public void saveResponse(String key, Object response) {
        log.info("Saving response with key: {}", key);
        redisTemplate.opsForValue().set(key, response, TTL, TimeUnit.MINUTES);
    }

    public Object getResponse(String key) {
        log.info("Getting response from redis for key: {}", key);
        return redisTemplate.opsForValue().get(key);
    }

    public String processPayment(PaymentRequest paymentRequest) {
        String output = "Payment processed successfully for id: "+ paymentRequest.getId();
        log.info(output);
        return output;
    }

    //Atomic operation [Better approach: use SETNX (set if not exists)]
    public boolean tryLock(String key) {
        return Boolean.TRUE.equals(
                redisTemplate.opsForValue().setIfAbsent(key, "LOCK", 10, TimeUnit.MINUTES)
        );
    }
}
