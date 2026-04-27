package com.microscopic.batchprocess.scheduler;

import com.microscopic.batchprocess.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private static final Logger log = LoggerFactory.getLogger(BatchScheduler.class);

    private final RecordService recordService;

    @Scheduled(cron = "0 30 2 * * 6") //At 02:30 on Saturday.
    public void runBatchJob() {
        log.info("Scheduler triggered for batch processing...");
        recordService.processBatchRecord();
    }
}
