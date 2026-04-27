package com.microscopic.externalservice.controller;

import com.microscopic.externalservice.dto.Post;
import com.microscopic.externalservice.service.ExternalApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ExternalApiController {

    private final ExternalApiService externalApiService;

    public ExternalApiController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

   /* @GetMapping("/resttemplate/post/{id}")
    public Post getPostRestTemplate(@PathVariable int id) {
        return externalApiService.getPostUsingRestTemplate(id);
    }*/

    @GetMapping("/webclient/post/{id}")
    public Mono<Post> getPostWebClient(@PathVariable int id) {
        return externalApiService.getPostUsingWebClient(id);
    }

    //WebClient API (List)
    @GetMapping("/webclient/posts")
    public Flux<Post> getAllPosts() {
        return externalApiService.getAllPostsUsingWebClient();
    }

    @GetMapping("/external/{id}")
    public Mono<String> getMessage(@PathVariable Long id) {
        return externalApiService.getMessage(id);
    }
}

/*
Key Interview Points
RestTemplate
    Blocking (thread waits)
    Simpler
    Deprecated (Spring recommends WebClient)
WebClient
    Non-blocking (Reactive)
    Uses Mono and Flux
    Better for high-performance & microservices
 */