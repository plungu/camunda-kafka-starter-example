package com.camunda.poc.starter.usecase.servicerequest.kafka.integration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageChannel;

@Profile("mspoc")
public interface MSSendEventPublishingSource {
    @Output("serviceRequestRecordChannelOutput")
    MessageChannel publish();
}
