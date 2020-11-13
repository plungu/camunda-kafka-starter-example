package com.camunda.poc.starter.usecase.servicerequest.kafka.integration;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.camunda.poc.starter.usecase.servicerequest.repo.ServiceRequestRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Profile;

import com.camunda.poc.starter.usecase.servicerequest.entity.ServiceRequestEntity;

@Profile("mspoc")
@EnableBinding(MSStartEventChannels.class)
public class MSStartEventSubscriber {

	private final Logger LOGGER = Logger.getLogger(Class.class.getName());

	public MSStartEventSubscriber(){}

	private RuntimeService runtimeService;
	private ServiceRequestRepository serviceRequestRepository;

	@Autowired
	public MSStartEventSubscriber(RuntimeService runtimeService, ServiceRequestRepository repository){
		this.runtimeService = runtimeService;
		this.serviceRequestRepository = repository;
	}

	@StreamListener("subscribeServiceRequest")
	public void handle(ServiceRequest serviceRequest) {
		LOGGER.info("/n/n Subscriber received Service Request: " + serviceRequest);

		ServiceRequestEntity entity = new ServiceRequestEntity(serviceRequest);
		serviceRequestRepository.save( entity );

		Map<String, Object> variables = new HashMap();
		variables.put("serviceRequest", serviceRequest);
		runtimeService.correlateMessage("start-service-request", serviceRequest.getServiceId(),variables);
	}
}
