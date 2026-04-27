package com.correlation.coorderservice.controller;

import com.correlation.coorderservice.context.TraceContext;
import com.correlation.coorderservice.service.CoorderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class CoorderController {

    private static final Logger log = LoggerFactory.getLogger(CoorderController.class);

    private final CoorderService coorderService;

    public CoorderController(CoorderService coorderService) {
        this.coorderService = coorderService;
    }

    @PostMapping("/create-order")
    public String createOrder() {
        String correlationId = UUID.randomUUID().toString();
        TraceContext.set(correlationId);
        log.info("Incoming request with correlationId - {}", correlationId);

        //coorderService.sendOrder();
        return "Order created successful";
    }
}
