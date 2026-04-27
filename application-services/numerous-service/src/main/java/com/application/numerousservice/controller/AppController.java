package com.application.numerousservice.controller;

import com.application.numerousservice.service.AppService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AppController {

    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    @PostMapping("/save")
    public String saveData(@RequestParam(name = "user") String userName,
                           @RequestParam(name = "product") String productName) {
        appService.saveData(userName, productName);
        return "Saved to both DBs!";
    }
}
