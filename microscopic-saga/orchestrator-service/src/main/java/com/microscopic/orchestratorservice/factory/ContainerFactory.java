package com.microscopic.orchestratorservice.factory;

import com.microscopic.events.InventoryReservedEvent;
import com.microscopic.events.OrderCreatedEvent;
import com.microscopic.events.PaymentProcessedEvent;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.stereotype.Component;

@Component
public class ContainerFactory {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> orderCreatedKafkaListenerContainerFactory (
            ConsumerFactory<String, OrderCreatedEvent> consumerFactory) {

        JacksonJsonDeserializer<OrderCreatedEvent> deserializer = new JacksonJsonDeserializer<>(OrderCreatedEvent.class);

        deserializer.addTrustedPackages("com.microscopic.events");
        deserializer.setUseTypeHeaders(false);

        DefaultKafkaConsumerFactory<String, OrderCreatedEvent> factory =
                new DefaultKafkaConsumerFactory<>(
                        consumerFactory.getConfigurationProperties(),
                        new StringDeserializer(),
                        deserializer
                );

        ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> listenerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();

        listenerFactory.setConsumerFactory(factory);

        return listenerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentProcessedEvent> paymentProcessedKafkaListenerContainerFactory (
            ConsumerFactory<String, PaymentProcessedEvent> consumerFactory) {

        JacksonJsonDeserializer<PaymentProcessedEvent> deserializer = new JacksonJsonDeserializer<>(PaymentProcessedEvent.class);

        deserializer.addTrustedPackages("com.microscopic.events");
        deserializer.setUseTypeHeaders(false);

        DefaultKafkaConsumerFactory<String, PaymentProcessedEvent> factory =
                new DefaultKafkaConsumerFactory<>(
                        consumerFactory.getConfigurationProperties(),
                        new StringDeserializer(),
                        deserializer
                );

        ConcurrentKafkaListenerContainerFactory<String, PaymentProcessedEvent> listenerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();

        listenerFactory.setConsumerFactory(factory);

        return listenerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InventoryReservedEvent> inventoryReservedKafkaListenerContainerFactory (
            ConsumerFactory<String, InventoryReservedEvent> consumerFactory) {

        JacksonJsonDeserializer<InventoryReservedEvent> deserializer = new JacksonJsonDeserializer<>(InventoryReservedEvent.class);

        deserializer.addTrustedPackages("com.microscopic.events");
        deserializer.setUseTypeHeaders(false);

        DefaultKafkaConsumerFactory<String, InventoryReservedEvent> factory =
                new DefaultKafkaConsumerFactory<>(
                        consumerFactory.getConfigurationProperties(),
                        new StringDeserializer(),
                        deserializer
                );

        ConcurrentKafkaListenerContainerFactory<String, InventoryReservedEvent> listenerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();

        listenerFactory.setConsumerFactory(factory);

        return listenerFactory;
    }
}
