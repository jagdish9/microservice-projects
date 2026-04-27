package com.appservice.callersecureservice.controller;

import com.appservice.callersecureservice.service.CallerSecureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/call")
public class CallerSecureController {

    private final CallerSecureService callerSecureService;

    public CallerSecureController(CallerSecureService callerSecureService) {
        this.callerSecureService = callerSecureService;
    }

    @GetMapping("/check-secure")
    public String callSecureApi() {
        return callerSecureService.callSecureApi();
    }
}
