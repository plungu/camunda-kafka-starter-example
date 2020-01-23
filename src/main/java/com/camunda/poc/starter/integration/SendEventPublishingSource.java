package com.camunda.poc.starter.integration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageChannel;

@Profile("integration")
public interface SendEventPublishingSource {
    @Output("contactRecordChannelOutput")
    MessageChannel publish();
}
