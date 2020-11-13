package com.camunda.poc.starter.integration;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import com.camunda.poc.starter.entity.Contact;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

@Profile("integration")
@EnableBinding(StartEventChannels.class)
public class StartEventSubscriber {

    @Autowired
    RuntimeService runtimeService;

    @StreamListener("subscribeContact")
    public void handle(Contact contact) {
        System.out.println("Received: " + contact);
        Map<String, Object> variables = new HashMap();
        variables.put("contact", contact);
        runtimeService.correlateMessage("start-service-request",
                contact.getBusinessKey(),
                variables);
    }
}
