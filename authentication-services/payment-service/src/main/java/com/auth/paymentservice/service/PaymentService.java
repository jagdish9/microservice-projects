package com.auth.paymentservice.service;

import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.*;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PaymentService {
    private final WebClient webClient;

    public PaymentService(ClientRegistrationRepository repo,
                          OAuth2AuthorizedClientService service) {

        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(repo, (OAuth2AuthorizedClientRepository) service);

        this.webClient = WebClient.builder()
                .apply(oauth.oauth2Configuration())
                .build();
    }

    public String callOrderService() {
        return webClient.get()
                .uri("http://localhost:9101/orders")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
