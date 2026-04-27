package com.appservice.redisidempotencyservice.controller;

import com.appservice.redisidempotencyservice.dto.PaymentRequest;
import com.appservice.redisidempotencyservice.service.IdempotencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/idempotency")
public class IdempotencyController {

    private static final Logger log = LoggerFactory.getLogger(IdempotencyController.class);

    private final IdempotencyService idempotencyService;

    public IdempotencyController(IdempotencyService idempotencyService) {
        this.idempotencyService = idempotencyService;
    }

    @PostMapping("/payment")
    public ResponseEntity<?> processPayment(
            @RequestHeader("Idempotency-key") String key,
            @RequestBody PaymentRequest paymentRequest
    ) {
        if(idempotencyService.isDuplicate(key)) {
            return ResponseEntity.ok(idempotencyService.getResponse(key));
        }

        //simulate processing
        Object response = idempotencyService.processPayment(paymentRequest);

        idempotencyService.saveResponse(key, response);

        return ResponseEntity.ok(response);
    }
}
