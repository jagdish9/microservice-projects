package com.microscopic.externalservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/downstream")
public class DownstreamController {

    private static final Logger log = LoggerFactory.getLogger(DownstreamController.class);

    @GetMapping("/slow")
    public Mono<String> slowApi() {
        return Mono.delay(Duration.ofSeconds(10))
                .map(i -> "Response from downstream service");
    }

    @GetMapping("/correlation")
    public Mono<String> correlation(ServerHttpRequest request) {
        String correlationId = request.getHeaders()
                .getFirst("X-Correlation-ID");

        log.info("Received Correlation ID: {}", correlationId);

        return Mono.just("OK");
    }
}
