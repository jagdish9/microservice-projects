package com.microscopic.analyticsservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AnalyticsService {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsService.class);

    private final RestTemplate restTemplate;

    public AnalyticsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getTagById(Long id) {
        String url = "http://localhost:9301/metadata/get-url/" + id;
        log.info("Url to be called - {}", url);
        return restTemplate.getForObject(url, String.class);
    }
}
