package com.microscopic.reactiveuserservice.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CorrelationIdFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(CorrelationIdFilter.class);

    public static final String CORRELATION_ID = "X-Correlation-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String correlationId = exchange.getRequest()
                .getHeaders()
                .getFirst(CORRELATION_ID);

        if(correlationId == null) {
            correlationId = UUID.randomUUID().toString();
            log.info("correlationId generated - {}", correlationId);
        } else {
            log.info("correlationId found in header - {}", correlationId);
        }

        //put into MDC
        //MDC.put(CORRELATION_ID, correlationId);

        //Add to response
        exchange.getResponse().getHeaders().set(CORRELATION_ID, correlationId);

        /*String finalCorrelationId = correlationId;
        return chain.filter(exchange)
                .contextWrite(ctx -> ctx.put(CORRELATION_ID, finalCorrelationId));*/

        //Mutate request, important for downstream propagation
        ServerHttpRequest mutatedRequest = exchange.getRequest()
                .mutate()
                .header(CORRELATION_ID, correlationId)
                .build();

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(mutatedRequest)
                .build();

        String finalCorrelationId = correlationId;

        //Use mutatedExchange here (NOT original exchange)
        return chain.filter(mutatedExchange)
                .contextWrite(ctx -> ctx.put(CORRELATION_ID, finalCorrelationId));
    }
}
