package com.application.paymentservice.service;

import com.application.paymentservice.entity.Payment;
import com.application.paymentservice.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(Payment payment) {
        payment.setCreatedAt(LocalDateTime.now());
        payment.setStatus("CREATED");
        log.info("New order created successfully - {}", payment.getOrderId());
        return paymentRepository.save(payment);
    }

    public Payment getPayment(String orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public Payment updatePayment(String orderId, Payment payment) {
        Payment existingPayment = getPayment(orderId);

        existingPayment.setOrderId(payment.getOrderId());
        existingPayment.setAmount(payment.getAmount());
        existingPayment.setStatus(payment.getStatus());
        log.info("Order is getting updated for orderId - {}", orderId);

        return paymentRepository.save(existingPayment);
    }

    public Payment updatePaymentStatus(String orderId, String status) {
        Payment existingPayment = getPayment(orderId);
        existingPayment.setStatus(status);

        log.info("Payment status is getting updated for orderId - {}", orderId);
        return paymentRepository.save(existingPayment);
    }

    @Transactional
    public Payment createNewPayment(Payment payment, String idempotencyKey) {
        return paymentRepository.findByIdempotencyKey(idempotencyKey)
                .orElseGet(() -> {
                    Payment existingPayment = Payment.builder()
                            .orderId(payment.getOrderId())
                            .amount(payment.getAmount())
                            .status("CREATED")
                            .createdAt(LocalDateTime.now())
                            .idempotencyKey(idempotencyKey)
                            .build();

                    return paymentRepository.save(existingPayment);
                });
    }
}
