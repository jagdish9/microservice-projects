package com.microscopic.reactiveuserservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8200")
                .filter(correlationIdFilter())
                .build();
    }

    private ExchangeFilterFunction correlationIdFilter() {
        return (request, next) ->
                Mono.deferContextual(contextView -> {
                    String correlationId = contextView.getOrDefault("X-Correlation-ID", "N/A");
                    ClientRequest newRequest = ClientRequest.from(request)
                            .header("X-Correlation-ID", correlationId)
                            .build();
                    return next.exchange(newRequest);
                });
    }
}
