package com.camunda.poc.starter.kafka.integration;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Profile;

@Profile("integration")
@EnableBinding(KafkaEventChannels.class)
public class KafkaEventPublisher {
}
