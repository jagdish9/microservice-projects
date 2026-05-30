package com.appservice.topicmanagementservice.runner;

import com.appservice.topicmanagementservice.service.TopicCreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartRunner implements CommandLineRunner {

    private final TopicCreatorService topicCreatorService;

    /*public StartRunner(TopicCreatorService topicCreatorService) {
        this.topicCreatorService = topicCreatorService;
    }*/ // or @RequiredArgsConstructor

    @Override
    public void run(String... args) throws Exception {
        topicCreatorService.createTopics();
    }
}
