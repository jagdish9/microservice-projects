package com.appservice.redisidempotencyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedisIdempotencyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisIdempotencyServiceApplication.class, args);
	}

}
