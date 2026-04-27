package com.microscopic.batchprocess.service;

import com.microscopic.batchprocess.entity.RecordEntity;
import com.microscopic.batchprocess.repository.RecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class RecordService {

    private static final Logger log = LoggerFactory.getLogger(RecordService.class);

    private final RecordRepository recordRepository;

    private final ExecutorService executorService;

    private static final int BATCH_SIZE = 1000;

    public RecordService(RecordRepository recordRepository, ExecutorService executorService) {
        this.recordRepository = recordRepository;
        this.executorService = executorService;
    }

    public void processBatchRecord() {
        log.info("Starting batch processing...");
        List<RecordEntity> records = recordRepository.findAll();

        log.info("Total records fetched: {}", records.size());

        for(int i = 0; i < records.size(); i = i + BATCH_SIZE) {
            int endBatch = Math.min(i+BATCH_SIZE, records.size());
            List<RecordEntity> batch = records.subList(i, endBatch);

            int batchNumber = i / BATCH_SIZE;

            executorService.submit(() -> processBatch(batch, batchNumber));
        }

        log.info("All batches submitted to executor service");
    }

    private void processBatch(List<RecordEntity> batch, int batchNumber) {
        log.info("Processing batch {} with {} records on thread {}",
                batchNumber, batch.size(), Thread.currentThread().getName());
        try {
            //simulate processing
            for(RecordEntity record : batch) {
                //business logic here
                record.setName(record.getName() + " - updated");

                log.info("Record data - {}", record.getName());
            }

            recordRepository.saveAll(batch);

            log.info("Completed batch {}", batch);

        } catch (Exception e) {
            log.error("Error processing batch {}", batchNumber, e);
        }
    }
}
