package com.camunda.poc.starter.usecase.servicerequest.kafka.integration;

import java.util.logging.Logger;

import com.camunda.poc.starter.usecase.servicerequest.entity.ServiceRequestEntity;
import com.camunda.poc.starter.usecase.servicerequest.repo.ServiceRequestRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;

@Profile("integration")
@EnableBinding(ServiceRequestEventChannels.class)
public class ServiceRequestEventSubscriber {

	private final Logger LOGGER = Logger.getLogger(Class.class.getName());

	public ServiceRequestEventSubscriber(){}

	private RuntimeService runtimeService;
	private ServiceRequestRepository serviceRequestRepository;

	@Autowired
	public ServiceRequestEventSubscriber(RuntimeService runtimeService, ServiceRequestRepository repository){
		this.runtimeService = runtimeService;
		this.serviceRequestRepository = repository;
	}

	@StreamListener("subscribeServiceRequest")
	public void handle(ServiceRequestEvent serviceRequestEvent) throws Exception {
		LOGGER.info("\n\n Subscriber received Service Request: " + serviceRequestEvent.getEventName());

		ServiceRequest serviceRequest = serviceRequestEvent.getServiceRequest();

		ServiceRequestEntity sre = serviceRequestRepository
				.findServiceRequestByServiceId(serviceRequest.getServiceId());

		if(sre != null) {
			if (serviceRequestEvent.getEventType().equalsIgnoreCase("UPDATE")){
				serviceRequestRepository.save(sre);
				LOGGER.info("\n\n  Service Request Entity Updated" + sre);
			}else if(serviceRequestEvent.getEventType().equalsIgnoreCase("CREATE")){
				LOGGER.info("\n\n  Service Request  Created: " + sre );
			}
		}else{
			throw new Exception("Error Updating Service Request");
		}

	}
}
