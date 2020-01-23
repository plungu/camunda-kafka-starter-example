package com.camunda.poc.starter.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.camunda.poc.starter.entity.Contact;

@Profile("integration")
@RestController
public class StartEventPublishingController {
    @Autowired
    StartEventChannels source;

    @PostMapping("/contact")
    public String contact(@RequestBody Contact contact){
        source.publish().send(MessageBuilder.withPayload(contact).build());
        System.out.println("Contact Payload Sent: "+contact.toString());
        return "Contact Payload Sent!";
    }
}
