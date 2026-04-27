package com.retrymechanism.eurekatestservice.controller;

import com.retrymechanism.eurekatestservice.service.EurekaTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eureka")
public class EurekaTestController {

    private final EurekaTestService eurekaTestService;

    public EurekaTestController(EurekaTestService eurekaTestService) {
        this.eurekaTestService = eurekaTestService;
    }

    @GetMapping("/product/{id}")
    public double price(@PathVariable Long id) {
        return eurekaTestService.price(id);
    }
}
