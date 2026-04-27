package com.microscopic.batchprocess.runner;

import com.microscopic.batchprocess.entity.RecordEntity;
import com.microscopic.batchprocess.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component //(If we don't want to run we can uninject)
public class DataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);

    private final RecordRepository recordRepository;

    public DataLoader(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting data load...");

        int totalRecords = 6000;
        int batchSize = 1000;

        for(int i = 0; i < totalRecords; i += batchSize) {

            List<RecordEntity> records = new ArrayList<>();

            for(int j = i; j < i + batchSize && j < totalRecords; j++) {
                records.add(RecordEntity.builder()
                        .name("Batch"+j)
                        .status("Active")
                        .build());
            }

            recordRepository.saveAll(records);

            log.info("Inserted batch from {} to {}", i, i + batchSize);
        }
        log.info("Data loading completed");
    }
}
