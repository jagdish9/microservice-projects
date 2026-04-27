package com.appservice.secureservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public").permitAll() //For API
                       // .requestMatchers("/admin").hasRole("ADMIN") //Role based access
                       // .requestMatchers("/admin").hasRole("USERKJ") //USER role is not mapped
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("ROLE_");

       JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
       jwtConverter.setJwtGrantedAuthoritiesConverter(converter);

       return jwtConverter;
    }
}

/*
Role based access (Important for interview)
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/admin").hasRole("ADMIN")
    .anyRequest().authenticated()
)
 */
