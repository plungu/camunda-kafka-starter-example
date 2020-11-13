package com.camunda.poc.starter.usecase.servicerequest.kafka.integration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

@Profile("mspoc")
public interface MSStartEventChannels {

    @Output("publishServiceRequest")
    MessageChannel publish();

    @Input("subscribeServiceRequest")
    SubscribableChannel start();
}
