package com.appservice.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimiterService {

    private static final int LIMIT = 10; // 10 requests per minute
    private static final int WINDOW = 60; // 60 seconds

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean isAllowed(String userId) {
        String key = "rate:" + userId;

        Long count = redisTemplate.opsForValue().increment(key);

        if(count != null && count == 1) {
            redisTemplate.expire(key, Duration.ofSeconds(WINDOW));
        }

        return count != null && count <= LIMIT;
    }
}
