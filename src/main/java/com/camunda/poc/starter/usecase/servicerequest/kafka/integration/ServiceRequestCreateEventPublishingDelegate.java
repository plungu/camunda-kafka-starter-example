package com.camunda.poc.starter.usecase.servicerequest.kafka.integration;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.camunda.poc.starter.usecase.servicerequest.repo.ServiceRequestRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


/**
 * This is an easy adapter implementation 
 * illustrating how a Java Delegate can be used 
 * from within a BPMN 2.0 Service Task.
 */
@Profile("servicerequest")
@Component("createEventPublishingDelegate")
public class ServiceRequestCreateEventPublishingDelegate implements JavaDelegate {
 
  private final Logger LOGGER = Logger.getLogger(ServiceRequestCreateEventPublishingDelegate.class.getName());


  private ServiceRequestEventChannels channels;
  private ServiceRequestRepository serviceRequestRepository;

  @Autowired
  public ServiceRequestCreateEventPublishingDelegate(ServiceRequestEventChannels source, ServiceRequestRepository serviceRequestRepository){
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

      sre.setEventName("CREATE-SERVICE-REQUEST");
      sre.setEventType("CREATE");

      channels.publish().send(MessageBuilder.withPayload(sre).build());

      LOGGER.info(" \n\n Service Request Payload Sent: "+sre);

  }

}
