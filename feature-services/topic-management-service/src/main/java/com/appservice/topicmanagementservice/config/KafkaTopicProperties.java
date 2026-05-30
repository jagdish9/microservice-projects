package com.appservice.topicmanagementservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "kafka")
public class KafkaTopicProperties {

    private List<TopicConfig> topics;
}
