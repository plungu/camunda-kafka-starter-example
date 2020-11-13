package com.camunda.poc.starter.usecase.servicerequest.controller;

import com.camunda.poc.starter.integration.SendEventPublishingDelegate;
import com.camunda.poc.starter.usecase.servicerequest.kafka.integration.MSStartEventChannels;
import com.camunda.poc.starter.usecase.servicerequest.kafka.integration.ServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Profile("mspoc")
@RestController
public class ServiceRequestController {

	private final Logger LOGGER = Logger.getLogger(Class.class.getName());

	public ServiceRequestController() {
	}

	MSStartEventChannels source;

	@Autowired
	public ServiceRequestController(MSStartEventChannels source) {
		this.source = source;
	}

	@RequestMapping(value = "/sr")
	public String index() {
		return "app";
	}


	@RequestMapping(value = "/sr/start", method = RequestMethod.POST, consumes = {"application/json"})
	public ResponseEntity<HttpStatus> start(@RequestBody(required = false) ServiceRequest serviceRequest)
	{
		LOGGER.info("/n/n Start Service Request"+ serviceRequest);

		source.publish().send(MessageBuilder.withPayload(serviceRequest).build());

		ResponseEntity<HttpStatus> response = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/sr/save", method = RequestMethod.POST, consumes = {"application/json"})
	public ResponseEntity<HttpStatus> save(@RequestBody(required = false) ServiceRequest serviceRequest)
	{

		LOGGER.info("/n/n  Save Service Request"+ serviceRequest);

		ResponseEntity<HttpStatus> response = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/sr/task/complete", method = RequestMethod.POST, consumes = {"application/json"})
	public ResponseEntity<HttpStatus> complete(@RequestBody(required = false) ServiceRequest serviceRequest,
											@RequestParam(value = "category", required = false) Boolean category,
											@RequestParam(value = "description") String description,
											@RequestParam(value = "owner") String owner,
											@RequestParam(value = "supplier") String supplier,
											@RequestParam(value = "serviceId", required = false) String serviceId)
	{

		LOGGER.info("/n/n  Complete Service Request Task"+ serviceRequest);

		ResponseEntity<HttpStatus> response = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		return response;
	}

}