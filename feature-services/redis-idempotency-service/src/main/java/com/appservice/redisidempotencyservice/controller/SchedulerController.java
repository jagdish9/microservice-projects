package com.appservice.redisidempotencyservice.controller;

import com.appservice.redisidempotencyservice.service.IdempotencyService;
import com.appservice.redisidempotencyservice.service.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
public class SchedulerController {

    private static final Logger log = LoggerFactory.getLogger(SchedulerController.class);

    private final SchedulerService schedulerService;

    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping("/start-scheduler")
    public String scheduleService() {
        return schedulerService.runScheduleJob();
    }
}
