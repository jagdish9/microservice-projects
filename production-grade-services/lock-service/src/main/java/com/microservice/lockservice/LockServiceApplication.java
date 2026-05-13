package com.microservice.lockservice;

import com.microservice.lockservice.entity.Account;
import com.microservice.lockservice.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LockServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LockServiceApplication.class, args);
	}

    /*@Bean
    CommandLineRunner initialize(AccountRepository repository) {
        return args -> {
            repository.save(new Account(null, "Sandeep", 1000.0, null));
            repository.save(new Account(null, "Megha", 1000.0, null));
        };
    }*/
}
