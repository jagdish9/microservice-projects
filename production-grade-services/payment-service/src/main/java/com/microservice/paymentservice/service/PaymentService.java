package com.microservice.paymentservice.service;

import com.microservice.commonevents.event.OrderEvent;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @CircuitBreaker(name = "paymentService", fallbackMethod = "fallback")
    public void processPayment(OrderEvent event) {
        log.info("Payment successful");
    }

    public void fallback(OrderEvent event, Throwable t) {
        log.info("Fallback triggered for order: {}", event.getOrderId());
    }
}
