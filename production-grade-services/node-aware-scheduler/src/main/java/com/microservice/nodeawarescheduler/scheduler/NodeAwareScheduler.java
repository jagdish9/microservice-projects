package com.microservice.nodeawarescheduler.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class NodeAwareScheduler {

    private static final Logger log = LoggerFactory.getLogger(NodeAwareScheduler.class);

    @Value("${scheduler.allowed.host}")
    private String allowedHost;

    @Scheduled(cron = "0 */1 * * * *") // Run every 1 minute
    public void runJob() throws Exception {
        String hostname = InetAddress.getLocalHost().getHostName();
        if (!hostname.equalsIgnoreCase(allowedHost)) {
            log.info("Current host '{}' is not the allowed host '{}'. Skipping job execution.", hostname, allowedHost);
            return; // Skip execution if not on the allowed host
        }

        log.info("Running job only on allowed host: {}", hostname);
    }
}
