package com.camunda.poc.starter.usecase.servicerequest.kafka.integration;

import java.util.logging.Logger;

import com.camunda.poc.starter.usecase.servicerequest.entity.ServiceRequestEntity;
import com.camunda.poc.starter.usecase.servicerequest.repo.ServiceRequestRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;

@Profile("servicerequest")
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
	public void handle(ServiceRequestEvent serviceRequestEvent) {
		LOGGER.info("\n\n Subscriber received Service Request: " + serviceRequestEvent.getEventName());

		ServiceRequest serviceRequest = serviceRequestEvent.getServiceRequest();

		ServiceRequestEntity entity = new ServiceRequestEntity(serviceRequest);
		LOGGER.info("\n\n  Service Request Entity Created" + entity);
		if(serviceRequestEvent.getEventType().equalsIgnoreCase("CREATE"))
			serviceRequestRepository.save( entity );

		LOGGER.info("\n\n  Service Request Entity Created" + entity);

	}
}
