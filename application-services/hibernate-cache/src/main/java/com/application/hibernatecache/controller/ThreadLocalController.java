package com.application.hibernatecache.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/thread")
public class ThreadLocalController {

    private static final ThreadLocal<List<String>> threadLocal =
            ThreadLocal.withInitial(ArrayList::new);

    @GetMapping("/threadlocal")
    public String testLeak() {

        try {
            List<String> list = threadLocal.get();

            for (int i = 0; i < 10000; i++) {
                list.add(UUID.randomUUID().toString() + " - Tue31Mar26");
            }

            //Missing cleanup //now added in finally block

            return "Added: " + list.size();
        } finally {
            threadLocal.remove(); //Best practice to avoid memory leak
        }
    }
}
