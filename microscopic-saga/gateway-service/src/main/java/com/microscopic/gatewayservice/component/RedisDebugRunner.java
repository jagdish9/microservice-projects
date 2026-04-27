package com.microscopic.gatewayservice.component;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RedisDebugRunner implements CommandLineRunner {

    private final ApplicationContext context;

    public RedisDebugRunner(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) {
        System.out.println("ReactiveRedisConnectionFactory: "
                + context.getBeansOfType(org.springframework.data.redis.connection.ReactiveRedisConnectionFactory.class));

        System.out.println("RedisRateLimiter: "
                + context.getBeansOfType(org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter.class));
    }
}
