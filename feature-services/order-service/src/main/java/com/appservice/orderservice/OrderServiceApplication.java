package com.appservice.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}

/*
You should create 2 Spring Boot microservices:

1. order-service
Produces Kafka events
Uses Redis for:
Rate limiting
Caching (optional)
2. payment-service
Consumes Kafka events
Uses Redis for:
Idempotency
Distributed locking (if needed)
 */