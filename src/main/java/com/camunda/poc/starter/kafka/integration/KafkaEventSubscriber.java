package com.camunda.poc.starter.kafka.integration;

import java.util.logging.Logger;

import com.camunda.poc.starter.entity.ServiceRequestEntity;
import com.camunda.poc.starter.repo.ServiceRequestRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;

@Profile("integration")
@EnableBinding(KafkaEventChannels.class)
public class KafkaEventSubscriber {

	private final Logger LOGGER = Logger.getLogger(Class.class.getName());

	public KafkaEventSubscriber(){}

	private RuntimeService runtimeService;
	private ServiceRequestRepository serviceRequestRepository;

	@Autowired
	public KafkaEventSubscriber(RuntimeService runtimeService, ServiceRequestRepository repository){
		this.runtimeService = runtimeService;
		this.serviceRequestRepository = repository;
	}

	@StreamListener("subscribeServiceRequest")
	public void handle(KafkaEvent kafkaEvent) throws Exception {
		LOGGER.info("\n\n Subscriber received Service Request: " + kafkaEvent.getEventName());

		ServiceRequest serviceRequest = kafkaEvent.getServiceRequest();

		ServiceRequestEntity sre = serviceRequestRepository
				.findServiceRequestByServiceId(serviceRequest.getServiceId());

		if(sre != null) {
			if (kafkaEvent.getEventType().equalsIgnoreCase("UPDATE")){
				serviceRequestRepository.save(sre);
				LOGGER.info("\n\n  Service Request Entity Updated" + sre);
			}else if(kafkaEvent.getEventType().equalsIgnoreCase("CREATE")){
				LOGGER.info("\n\n  Service Request  Created: " + sre );
			}
		}else{
			throw new Exception("Error Updating Service Request");
		}

	}
}
