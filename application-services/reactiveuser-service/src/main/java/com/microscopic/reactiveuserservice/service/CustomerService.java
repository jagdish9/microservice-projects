package com.microscopic.reactiveuserservice.service;

import com.microscopic.reactiveuserservice.entity.Customer;
import com.microscopic.reactiveuserservice.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Service
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final KafkaProducerService kafkaProducerService;
    private final WebClient webClient;

    public CustomerService(CustomerRepository customerRepository,
                           KafkaProducerService kafkaProducerService,
                           WebClient webClient) {
        this.customerRepository = customerRepository;
        this.kafkaProducerService = kafkaProducerService;
        this.webClient = webClient;
    }

    //save customer and send event and return saved customer
    public Mono<Customer> createCustomer(Customer customer) {
        return customerRepository.save(customer)
                .flatMap(savedUser ->
                        kafkaProducerService.sendEvent("Customer Created: "+ savedUser.getId())
                                .thenReturn(savedUser)
                );
    }

    // Get all customers streaming
    public Flux<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    //combine DB + external API
    public Mono<Map<String, Object>> getCustomerWithExternal(Long id) {
        Mono<Customer> customerMono = customerRepository.findById(id);
        Mono<String> externalMono = callExternalService(id);

        return Mono.zip(customerMono, externalMono)
                .map(tuple -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("customer", tuple.getT1());
                    response.put("externalData", tuple.getT2());
                    return response;
                });
    }

    private Mono<String> callExternalService(Long id) {
        return webClient.get()
                .uri("/api/external/{id}", id)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> testTimeout(Long id) {
        return webClient.get()
                .uri("/api/external/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(3))
                .retry(2)
                .onErrorResume(ex -> {
                    if(ex instanceof TimeoutException) {
                        return Mono.just("Timeout fallback response");
                    }
                    return Mono.error(ex);
                });
    }

    public Mono<String> callDownstream() {
        return webClient.get()
                .uri("/downstream/slow")
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(2))
                .retryWhen(Retry.backoff(2, Duration.ofMillis(500)))
                .onErrorResume(ex -> {
                    log.info("Timeout error from downstream service - {}", ex.getMessage());
                    return Mono.just("Timeout fallback response");
                });
    }

    public Mono<String> callCorrelation() {
        return webClient.get()
                .uri("/downstream/correlation")
                .retrieve()
                .bodyToMono(String.class);
    }
}
