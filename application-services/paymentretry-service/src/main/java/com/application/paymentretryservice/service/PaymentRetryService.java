package com.application.paymentretryservice.service;

import com.application.paymentretryservice.domain.PaymentStatus;
import com.application.paymentretryservice.entity.Payment;
import com.application.paymentretryservice.repository.PaymentRetryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentRetryService {

    private final PaymentRetryRepository paymentRetryRepository;

    public PaymentRetryService(PaymentRetryRepository paymentRetryRepository) {
        this.paymentRetryRepository = paymentRetryRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void retryFailedPayment() {
        List<Payment> failedPayments = paymentRetryRepository.findByStatusAndRetryCountLessThan(PaymentStatus.FAILED.name(), 3);

        for(Payment payment : failedPayments) {
            try {
                // simulate external API call
                boolean success = callPaymentGateway(payment);

                if(success) {
                    payment.setStatus(PaymentStatus.SUCCESS.name());
                } else {
                    payment.setRetryCount(payment.getRetryCount() + 1);
                }

                payment.setLastAttemptTime(LocalDateTime.now());
                paymentRetryRepository.save(payment);

            } catch (Exception ex) {
                payment.setRetryCount(payment.getRetryCount() + 1);
                paymentRetryRepository.save(payment);
            }
        }
    }

    public boolean callPaymentGateway(Payment payment) {
        // simulate retry logic (replace with real API call)
        return Math.random() > 0.5;
    }
}
