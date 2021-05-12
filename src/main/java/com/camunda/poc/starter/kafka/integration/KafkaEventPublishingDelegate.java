package com.camunda.poc.starter.kafka.integration;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.camunda.poc.starter.entity.ServiceRequestEntity;
import com.camunda.poc.starter.repo.ServiceRequestRepository;
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
@Profile("integration")
@Component("eventPublishingDelegate")
public class KafkaEventPublishingDelegate implements JavaDelegate {
 
  private final Logger LOGGER = Logger.getLogger(KafkaEventPublishingDelegate.class.getName());


  private KafkaEventChannels channels;
  private ServiceRequestRepository serviceRequestRepository;

  @Autowired
  public KafkaEventPublishingDelegate(KafkaEventChannels source, ServiceRequestRepository serviceRequestRepository){
    this.channels = source;
    this.serviceRequestRepository = serviceRequestRepository;
  }

  public void execute(DelegateExecution execution) throws Exception {

      String businessKey = execution.getBusinessKey();
      Boolean approved = (Boolean) execution.getVariable("approved");

      ServiceRequestEntity srEntity = serviceRequestRepository.findServiceRequestByServiceId(businessKey);
      ServiceRequest serviceRequest = new ServiceRequest(srEntity);

      Map eventParams = new HashMap();
      if (approved != null)
          eventParams.put("approved", approved);

      KafkaEvent sre = new KafkaEvent(serviceRequest, eventParams);

      sre.setEventName("CREATE-SERVICE-REQUEST");
      sre.setEventType("CREATE");

      channels.publish().send(MessageBuilder.withPayload(sre).build());

      LOGGER.info(" \n\n Service Request Payload Sent: "+sre);

  }

}
