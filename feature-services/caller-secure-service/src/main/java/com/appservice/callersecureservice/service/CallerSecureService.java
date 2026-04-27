package com.appservice.callersecureservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CallerSecureService {

    private static final Logger log = LoggerFactory.getLogger(CallerSecureService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${constant.oauth2.realm-uri}")
    private String tokenUrl;

    public String callSecureApi() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials"); // this is for service to service communication
        body.add("client_id", "spring-boot-app"); //need to store in vault
        body.add("client_secret", "ONa41BWTmMaPlo2nBO2E8fQrsSLtAOfV"); ////need to store in vault

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

        String accessToken = (String) response.getBody().get("access_token");

        log.info("Got the access token");

        HttpHeaders apiHeaders = new HttpHeaders();
        apiHeaders.setBearerAuth(accessToken);

        HttpEntity<String> apiRequest = new HttpEntity<>(apiHeaders);

        log.info("Calling the secure api");
        ResponseEntity<String> apiResponse = restTemplate.exchange(
                "http://localhost:8035/api/auth/user/profile",
                HttpMethod.GET,
                apiRequest,
                String.class
        );

        log.info("Api call ran successfully, output is: {}", apiResponse.getBody());
        return apiResponse.getBody();
    }
}
