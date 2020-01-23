package com.camunda.poc.starter.integration;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Profile;

@Profile("integration")
@EnableBinding(SendEventPublishingSource.class)
public class SendEventPublisher {
}
