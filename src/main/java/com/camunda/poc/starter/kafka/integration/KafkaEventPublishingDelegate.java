package com.camunda.poc.starter.kafka.integration;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.camunda.poc.starter.entity.Order;
import com.camunda.poc.starter.entity.ServiceRequestEntity;
import com.camunda.poc.starter.repo.OrderRepository;
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

//  private OrderRepository repository;

//  @Autowired
//  public KafkaEventPublishingDelegate(KafkaEventChannels source, OrderRepository repository){
//    this.channels = source;
//    this.repository = repository;
//  }

    @Autowired
    public KafkaEventPublishingDelegate(KafkaEventChannels source){
        this.channels = source;
    }

    public void execute(DelegateExecution execution) throws Exception {

        String businessKey = execution.getBusinessKey();
        Boolean approved = (Boolean) execution.getVariable("approved");

        //Get business data
        //Order bizEntity = repository.findOrderByOrderKey(businessKey);
        //KafkaRequestMapper kafkaRequestMapper = new KafkaRequestMapper(bizEntity);

        KafkaRequestMapper kafkaRequestMapper = new KafkaRequestMapper();

        Map eventParams = new HashMap();
        if (approved != null)
          eventParams.put("approved", approved);

        if (businessKey != null)
          eventParams.put("businessKey", businessKey);


        KafkaEvent event = new KafkaEvent(kafkaRequestMapper, eventParams);

        event.setEventName(KafkaEvent.UPDATE_WORKFLOW_EVENT);
        event.setEventType("CREATE");

        channels.publish().send(MessageBuilder.withPayload(event).build());

        LOGGER.info(" \n\n Kafka Payload Sent: "+event);
  }

}
