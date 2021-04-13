package com.camunda.poc.starter.usecase.servicerequest.kafka.integration;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Profile;

@Profile("integration")
@EnableBinding(ServiceRequestEventChannels.class)
public class ServiceRequestEventPublisher {
}
