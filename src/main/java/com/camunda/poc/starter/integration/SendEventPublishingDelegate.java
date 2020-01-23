package com.camunda.poc.starter.integration;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.camunda.poc.starter.entity.Contact;

import java.util.logging.Logger;


/**
 * This is an easy adapter implementation 
 * illustrating how a Java Delegate can be used 
 * from within a BPMN 2.0 Service Task.
 */
@Profile("integration")
@Component("sendEventPublishingDelegate")
public class SendEventPublishingDelegate implements JavaDelegate {
 
  private final Logger LOGGER = Logger.getLogger(SendEventPublishingDelegate.class.getName());

  @Autowired
  SendEventPublishingSource source;

  public void execute(DelegateExecution execution) throws Exception {

      String businessKey = execution.getBusinessKey();
      Contact contact = (Contact) execution.getVariable("contact");

      source.publish().send(MessageBuilder.withPayload(contact).build());
      System.out.println("Contact Payload Sent: "+contact.toString());


  }

}
