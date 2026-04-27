package com.appservice.secureservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureServiceController {

    @GetMapping("/admin")
    public String adminApi() {
        return "This is admin api";
    }

    @GetMapping("/public")
    public String publicApi() {
        return "This is public";
    }

    @GetMapping("/secure")
    public String secureApi() {
        return "This is secure";
    }
}
