package com.appservice.topicmanagementservice.config;

import lombok.Data;

@Data
public class TopicConfig {
    private String name;
    private int partitions;
    private int replicationFactor;
}
