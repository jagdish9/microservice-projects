package com.application.hibernatecache.controller;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StatsController {

    @Autowired
    private EntityManagerFactory emf;

    @GetMapping("/stats")
    public String stats() {
        SessionFactory sf = emf.unwrap(SessionFactory.class);
        Statistics stats = sf.getStatistics();

        return "L2 Hits: "+ stats.getSecondLevelCacheHitCount()
                + "\nL2 Miss: "+ stats.getSecondLevelCacheMissCount()
                + "\nL2 Put: "+ stats.getSecondLevelCachePutCount();
    }
}
