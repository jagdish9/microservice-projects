package com.microscopic.basicauthservice.repository;

import org.springframework.boot.SpringBootVersion;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/spring")
public class BasicAuthController {

    @GetMapping("/monitoring-version")
    public Map<String, String> springVersion() {
        Map<String, String> response = new HashMap<>();
        response.put("spring-version", SpringBootVersion.getVersion());
        return response;
    }
}
