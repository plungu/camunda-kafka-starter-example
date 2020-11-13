package com.camunda.poc.starter.usecase.servicerequest.kafka.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Profile("mspoc")
@RestController
public class MSStartEventPublishingController {

    @Autowired
    MSStartEventChannels source;

    @PostMapping("/service")
    public String createservice(@RequestBody ServiceRequest serviceRequest){
        source.publish().send(MessageBuilder.withPayload(serviceRequest).build());
        System.out.println("Service Payload Sent: "+serviceRequest);
        return "service-request-app";
    }

}
