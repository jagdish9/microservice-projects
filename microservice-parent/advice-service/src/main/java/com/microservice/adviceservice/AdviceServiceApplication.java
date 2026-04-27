package com.microservice.adviceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AdviceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdviceServiceApplication.class, args);
	}

}
