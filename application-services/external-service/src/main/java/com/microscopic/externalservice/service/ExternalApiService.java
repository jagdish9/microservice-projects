package com.microscopic.externalservice.service;

import com.microscopic.externalservice.dto.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ExternalApiService {

    //private final RestTemplate restTemplate;

    /*public ExternalApiService(RestTemplate restTemplate, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }*/

    /*public Post getPostUsingRestTemplate(int id) {
        String url = "https://jsonplaceholder.typicode.com/posts/" + id;
        return restTemplate.getForObject(url, Post.class);
    }*/

    private final WebClient webClient;

    public ExternalApiService(WebClient webClient) {
        this.webClient = webClient;
    }


    public Mono<Post> getPostUsingWebClient(int id) {
        return webClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .bodyToMono(Post.class);

        // above can be converted into blocking
        /*Post post = webClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .bodyToMono(Post.class)
                .block();*/
    }

    public Flux<Post> getAllPostsUsingWebClient() {
        return webClient.get()
                .uri("/posts")
                .retrieve()
                .bodyToFlux(Post.class);
    }

    public Mono<String> getMessage(Long id) {
        return webClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .toEntity(Post.class)
                .delayElement(Duration.ofSeconds(4)) // 4 seconds delay to test timeout
                .map(m -> m.getBody().getTitle());
    }
}
