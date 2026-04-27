package com.application.paymentretryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PaymentretryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentretryServiceApplication.class, args);
	}

}
