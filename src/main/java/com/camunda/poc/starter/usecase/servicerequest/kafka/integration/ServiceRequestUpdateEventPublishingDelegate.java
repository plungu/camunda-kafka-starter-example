package com.camunda.poc.starter.usecase.servicerequest.kafka.integration;

import com.camunda.poc.starter.usecase.servicerequest.repo.ServiceRequestRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * This is an easy adapter implementation 
 * illustrating how a Java Delegate can be used 
 * from within a BPMN 2.0 Service Task.
 */
@Profile("mspoc")
@Component("updateEventPublishingDelegate")
public class ServiceRequestUpdateEventPublishingDelegate implements JavaDelegate {

  private final Logger LOGGER = Logger.getLogger(ServiceRequestUpdateEventPublishingDelegate.class.getName());


  private ServiceRequestEventChannels channels;
  private ServiceRequestRepository serviceRequestRepository;

  @Autowired
  public ServiceRequestUpdateEventPublishingDelegate(ServiceRequestEventChannels source, ServiceRequestRepository serviceRequestRepository){
    this.channels = source;
    this.serviceRequestRepository = serviceRequestRepository;
  }

  public void execute(DelegateExecution execution) throws Exception {

      String businessKey = execution.getBusinessKey();
      Boolean approved = (Boolean) execution.getVariable("approved");

//      ServiceRequestEntity srEntity = serviceRequestRepository.findServiceRequestByServiceId(businessKey);
//      ServiceRequest serviceRequest = new ServiceRequest(srEntity);

      ServiceRequest serviceRequest = (ServiceRequest)execution.getVariable("serviceRequest");

      Map eventParams = new HashMap();
      if (approved != null)
          eventParams.put("approved", approved);

      ServiceRequestEvent sre = new ServiceRequestEvent(serviceRequest, eventParams);

      sre.setEventName("UPDATE-SERVICE-REQUEST");
      sre.setEventType("UPDATE");

      channels.publish().send(MessageBuilder.withPayload(sre).build());

      LOGGER.info(" \n\n Service Request Payload Sent: "+sre);

  }

}
