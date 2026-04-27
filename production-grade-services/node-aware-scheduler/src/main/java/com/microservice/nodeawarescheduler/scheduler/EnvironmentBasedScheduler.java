package com.microservice.nodeawarescheduler.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentBasedScheduler {

    private static final Logger log = LoggerFactory.getLogger(EnvironmentBasedScheduler.class);

    @Value("${scheduler.enabled:false}")
    private boolean schedulerEnabled;

    @Scheduled(cron = "0 */1 * * * *") // Run every 1 minute
    public void runJob() {
        if(!schedulerEnabled) {
            log.info("Scheduler is disabled via configuration. Skipping job execution.");
            return;
        }

        log.info("Running job as scheduler is enabled.");

        try {
            // Simulate job work
            log.info("Job is running...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Job interrupted", e);
        }
    }
}
