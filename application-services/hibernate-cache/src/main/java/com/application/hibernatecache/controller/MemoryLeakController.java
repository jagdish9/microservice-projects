package com.application.hibernatecache.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/leak")
public class MemoryLeakController {

    private static final List<String> cache = new ArrayList<>();

    @GetMapping("/add-data")
    public String addData() {
        for(int i = 0; i < 10000; i++) {
            cache.add(UUID.randomUUID().toString());
        }

        return "Added data, current size: "+ cache.size();
    }
}
