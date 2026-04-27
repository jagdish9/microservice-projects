package com.auth.orderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping
    public String orders() {
        return "Orders API - Secured";
    }

    @GetMapping("/public")
    public String publicApi() {
        return "Public API";
    }
}
