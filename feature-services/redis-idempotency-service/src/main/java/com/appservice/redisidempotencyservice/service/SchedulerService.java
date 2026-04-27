package com.appservice.redisidempotencyservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SchedulerService {

    private static final Logger log = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Scheduled(fixedRate = 60000)
    public String runScheduleJob() {
        String lockKey = "scheduler_lock";
        Boolean lock = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, "locked", 2, TimeUnit.MINUTES);

        if(Boolean.TRUE.equals(lock)) {
            log.info("Created lock for processing: {}", lockKey);
            try {
                log.info("Running job by: {}", Thread.currentThread().getName());

                //actual job logic, for now sleeping for 50 sec
                Thread.sleep(50000);
            } catch (InterruptedException ie) {
                log.error("Error while running the job: {}", ie.getMessage());
                ie.printStackTrace();
            } finally {
                log.info("Deleting the created lock: {}", lockKey);
                redisTemplate.delete(lockKey);
            }
        } else {
            log.info("Already locked, processing by another instance");
        }

        return "Scheduled job run is completed successfully";
    }
}
