package com.microscopic.batchprocess.controller;

import com.microscopic.batchprocess.service.RecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping("/process-batch")
    public String processBatch() {
        recordService.processBatchRecord();
        return "Batch job(s) are scheduled";
    }
}
