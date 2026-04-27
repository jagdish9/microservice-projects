package com.microservice.schedulerlockservice.scheduler;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.LocalDateTime;

@Component
public class ScheduledJob {

    private static final Logger log = LoggerFactory.getLogger(ScheduledJob.class);

    @Scheduled(cron = "0 */1 * * * *") // Every 1 minute
    @SchedulerLock(name = "scheduleJob", lockAtLeastFor = "30s", lockAtMostFor = "2m")
    public void runJob() {
        log.info("Executing job at: {} | Instance: {}", LocalDateTime.now(), getInstance());

        try {
            log.info("Job is running...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Job interrupted", e);
        }
    }

    private String getInstance() {
        return InetAddress.getLoopbackAddress().getHostName();
    }
}
