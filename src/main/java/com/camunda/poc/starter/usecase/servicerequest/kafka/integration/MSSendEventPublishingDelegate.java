package com.camunda.poc.starter.usecase.servicerequest.kafka.integration;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.camunda.poc.starter.usecase.servicerequest.entity.ServiceRequestEntity;


/**
 * This is an easy adapter implementation 
 * illustrating how a Java Delegate can be used 
 * from within a BPMN 2.0 Service Task.
 */
@Profile("mspoc")
@Component("sendEventPublishingDelegate")
public class MSSendEventPublishingDelegate implements JavaDelegate {
 
  private final Logger LOGGER = Logger.getLogger(MSSendEventPublishingDelegate.class.getName());

  @Autowired
  MSSendEventPublishingSource source;

  public void execute(DelegateExecution execution) throws Exception {
      String businessKey = execution.getBusinessKey();
      ServiceRequestEntity sr = (ServiceRequestEntity) execution.getVariable("serviceRequest");
      source.publish().send(MessageBuilder.withPayload(sr).build());
      System.out.println("Service Request Payload Sent: "+sr);


  }

}
