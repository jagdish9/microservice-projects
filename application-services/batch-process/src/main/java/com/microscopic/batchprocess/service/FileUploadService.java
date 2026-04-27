package com.microscopic.batchprocess.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private static final Logger log = LoggerFactory.getLogger(FileUploadService.class);

    private final KafkaTemplate<String, List<String>> kafkaTemplate;

    public List<String> parseFile(MultipartFile file) {
        log.info("Started reading file");
        List<String> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream())
        )) {
            String line;

            while((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }
        log.info("Reading file is completed, now publishing message to the topic");
        kafkaTemplate.send("file-topic", lines);
        return lines;
    }
}
