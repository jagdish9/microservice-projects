package com.retrymechanism.eurekatestservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EurekaTestService {

    private static final Logger log = LoggerFactory.getLogger(EurekaTestService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${services.order}")
    private String orderService;

    public double price(Long id) {
        String url = "http://" + orderService.toUpperCase() + "/orders/test/"+ id;
        log.info("Calling api: {}", url);
        return restTemplate.getForObject(url, Long.class);
    }
}
