package com.microservice.nodeawarescheduler.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class PrefixBasedScheduler {

    private static final Logger log = LoggerFactory.getLogger(PrefixBasedScheduler.class);

    @Value("${scheduler.prefix}")
    private String hostnamePrefix;

    @Scheduled(cron = "0 */1 * * * *") // Run every 1 minute
    public void runJob() throws Exception {
        if(hostnamePrefix == null || hostnamePrefix.isEmpty()) {
            log.warn("Hostname prefix is not configured. Skipping job execution.");
            return;
        }

        try {
            String hostname = InetAddress.getLocalHost().getHostName();

            if(hostname.startsWith(hostnamePrefix)) {
                log.info("Running job on host '{}' which matches prefix '{}'", hostname, hostnamePrefix);
            } else {
                log.info("Current host '{}' does not match prefix '{}'. Skipping job execution.", hostname, hostnamePrefix);
            }
        } catch (Exception e) {
            log.error("Error while determining hostname or executing job: ", e);
        }
    }
}
