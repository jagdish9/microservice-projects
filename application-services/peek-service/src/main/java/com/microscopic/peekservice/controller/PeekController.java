package com.microscopic.peekservice.controller;

import com.microscopic.peekservice.service.PeekService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/peek")
public class PeekController {

    private static final Logger log = LoggerFactory.getLogger(PeekController.class);

    private final PeekService peekService;

    public PeekController(PeekService peekService) {
        this.peekService = peekService;
    }
}
