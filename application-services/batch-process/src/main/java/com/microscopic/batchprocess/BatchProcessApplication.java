package com.microscopic.batchprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BatchProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchProcessApplication.class, args);
	}

}
