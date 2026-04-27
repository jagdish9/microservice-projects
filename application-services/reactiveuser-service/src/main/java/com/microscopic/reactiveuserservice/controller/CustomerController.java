package com.microscopic.reactiveuserservice.controller;

import com.microscopic.reactiveuserservice.entity.Customer;
import com.microscopic.reactiveuserservice.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/create-customer")
    public Mono<Customer> createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping("/get-customers")
    public Flux<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}/details")
    public Mono<Map<String, Object>> getDetails(@PathVariable Long id) {
        return customerService.getCustomerWithExternal(id);
    }

    @GetMapping("/test-timeout/{id}")
    public Mono<String> testTimeout(@PathVariable Long id) {
        return customerService.testTimeout(id);
    }

    @GetMapping("/call-totest-timeout")
    public Mono<String> callDownstream() {
        return customerService.callDownstream();
    }

    @GetMapping("/call-totest-correlation")
    public Mono<String> callCorrelation() {
        return customerService.callCorrelation();
    }
}
