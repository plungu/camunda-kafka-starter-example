package com.camunda.poc.starter.usecase.servicerequest.kafka.integration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

@Profile("integration")
public interface ServiceRequestEventChannels {

    @Output("publishServiceRequest")
    MessageChannel publish();

    @Input("subscribeServiceRequest")
    SubscribableChannel start();
}
