package com.appservice.topicmanagementservice.service;

import com.appservice.topicmanagementservice.config.KafkaTopicProperties;
import com.appservice.topicmanagementservice.config.TopicConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
@Slf4j
public class TopicCreatorService {

    private final AdminClient adminClient;
    private final KafkaTopicProperties properties;

    public TopicCreatorService(AdminClient adminClient, KafkaTopicProperties properties) {
        this.adminClient = adminClient;
        this.properties = properties;
    }

    public void createTopics() {
        try {
            // Existing topics
            Set<String> existingTopics = adminClient.listTopics().names().get();

            log.info("Existing topics: {}", existingTopics);

            for (TopicConfig topic : properties.getTopics()) {
                if(existingTopics.contains(topic.getName())) {
                    log.info("Topic already exists: {}", topic.getName());
                    continue;
                }

                NewTopic newTopic = new NewTopic(
                        topic.getName(),
                        topic.getPartitions(),
                        (short) topic.getReplicationFactor()
                );

                try {
                    adminClient.createTopics(
                            Collections.singleton(newTopic)
                    ).all().get();

                    log.info("Topic created successfully: {}", topic.getName());
                } catch (Exception e) {
                    Throwable cause = e.getCause();

                    if(cause instanceof TopicExistsException) {
                        log.info("Topic already exists: {}", topic.getName());
                    } else {
                        log.error("Error creating topic: {}", topic.getName(), e);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error while managing topics", e);
        }
    }
}
