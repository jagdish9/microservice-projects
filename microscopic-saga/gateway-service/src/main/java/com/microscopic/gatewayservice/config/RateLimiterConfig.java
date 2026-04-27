package com.microscopic.gatewayservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.UUID;

@Configuration
public class RateLimiterConfig {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterConfig.class);
    /*@Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {

            //Try to get User ID from header
            String userId = exchange.getRequest()
                    .getHeaders()
                    .getFirst("X-User-Id");

            if(userId != null && userId.isEmpty()) {
                return Mono.just(userId);
            }

            String clientIp = exchange.getRequest()
                    .getRemoteAddress()
                    .getAddress()
                    .getHostAddress();

            return Mono.just(clientIp);
        };
    }*/

    /*@Bean
    public KeyResolver userKeyResolver() {
        return exchange ->
                exchange.getPrincipal()
                        .map(principal -> principal.getName())
                        .defaultIfEmpty("anonymous");
    }*/

    /*working without nginx (You are using Nginx / reverse proxy
    Nginx forwards the request → Spring Gateway sees:
    RemoteAddress = null OR unresolved) */

    /*@Bean
    public KeyResolver userKeyResolver() {
        return exchange ->
                Mono.just(
                        exchange.getRequest()
                                .getRemoteAddress()
                                .getAddress()
                                .getHostAddress()
                );
    }*/

    //working with nginx
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String ip = exchange.getRequest()
                    .getHeaders()
                    .getFirst("X-Forwarded-For");

            if(ip != null) {
                ip = ip.split(",")[0];
                log.info("Resolved IP: {}", ip);
                return Mono.just(ip); //First IP
            }

            InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();

            if(remoteAddress != null && remoteAddress.getAddress() != null) {
                String hostAddress = remoteAddress.getAddress().getHostAddress();
                log.info("Remote host address: {}", hostAddress);
                return Mono.just(hostAddress);
            }

            String uuid = UUID.randomUUID().toString();
            log.info("Random uuid: {}", uuid);
            return Mono.just(uuid);
        };
    }
}
