package com.microscopic.metadataservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class GitHubClient {

    @Autowired
    private RestTemplate restTemplate;

    public List<?> fetchTags(String owner, String repo) {
        String url = "https://api.github.com/repos/"+owner + "/" + repo + "/tags";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("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");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                List.class
        );
        return response.getBody();
    }
}
