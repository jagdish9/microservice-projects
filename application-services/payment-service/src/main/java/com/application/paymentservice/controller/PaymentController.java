package com.application.paymentservice.controller;

import com.application.paymentservice.entity.Payment;
import com.application.paymentservice.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    //create new resource
    @PostMapping("create-payment")
    public ResponseEntity<Payment> create(@RequestBody Payment payment) {
        Payment newPayment = paymentService.createPayment(payment);
        return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
    }

    //Get by id
    @GetMapping("/{orderId}")
    public ResponseEntity<Payment> get(@PathVariable String orderId) {
        return ResponseEntity.ok(paymentService.getPayment(orderId));
    }

    //Full update
    @PutMapping("/{orderId}")
    public ResponseEntity<Payment> update(
            @PathVariable String orderId,
            @RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.updatePayment(orderId, payment));
    }

    //Partial update
    @PatchMapping("/{orderId}")
    public ResponseEntity<Payment> updateStatus(
            @PathVariable String orderId,
            @RequestParam String status) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(orderId, status));
    }

    @PostMapping("/create-new-payment")
    public ResponseEntity<Payment> createNewPayment(@RequestBody Payment payment,
                                                    @RequestHeader("Idempotency-key") String idempotencyKey) {
        Payment newPayment = paymentService.createNewPayment(payment, idempotencyKey);
        return new ResponseEntity<>(newPayment, HttpStatus.CREATED);
    }
}
