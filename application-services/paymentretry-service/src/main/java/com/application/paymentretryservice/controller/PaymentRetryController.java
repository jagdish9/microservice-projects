package com.application.paymentretryservice.controller;

import com.application.paymentretryservice.log.AppLogger;
import com.application.paymentretryservice.service.PaymentRetryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@RestController

public class PaymentRetryController {

    private final PaymentRetryService paymentRetryService;

   // private static final Logger log = LoggerFactory.getLogger(PaymentRetryController.class);

    private static final AppLogger log = AppLogger.getInstance();

    public PaymentRetryController(PaymentRetryService paymentRetryService) {
        this.paymentRetryService = paymentRetryService;
    }

    @Scheduled(cron = "*/5 * * * *")
    public void retryPayment() {
        log.info("Running scheduled retry job...");
        paymentRetryService.retryFailedPayment();
    }
}

/*
“In our payment microservice, we used @Scheduled to retry failed transactions.
Failed payments were stored in the database, and a scheduled job periodically fetched and retried them.
 We implemented retry limits, idempotency, and distributed locking using ShedLock to avoid duplicate
  execution across instances.
 This helped ensure reliability in case of transient failures.”
 */