package com.camunda.poc.starter.usecase.servicerequest.controller.ui;

import com.camunda.poc.starter.usecase.servicerequest.entity.ServiceRequestEntity;
import com.camunda.poc.starter.usecase.servicerequest.kafka.integration.ServiceRequest;
import com.camunda.poc.starter.usecase.servicerequest.repo.ServiceRequestRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Profile("servicerequest")
@Controller
public class UiServiceRequestController {

	private final Logger LOGGER = Logger.getLogger(Class.class.getName());

	public UiServiceRequestController() {
	}

	private ServiceRequestRepository serviceRequestRepository;

	@Autowired
	public UiServiceRequestController(ServiceRequestRepository serviceRequestRepository) {
		this.serviceRequestRepository = serviceRequestRepository;
	}

	@RequestMapping("/sr")
	public String index() {
		return "app";
	}


	@RequestMapping(value = "/sr/save", method = RequestMethod.POST, consumes = {"application/json"})
	public ResponseEntity<HttpStatus> save(@RequestBody(required = false) ServiceRequest serviceRequest)
	{
		LOGGER.info("\n\n Save Service Request: "+ serviceRequest.getId());

		ServiceRequestEntity sre = serviceRequestRepository.findServiceRequestByServiceId(serviceRequest.getServiceId());

		try {
			sre.setServiceRequest(serviceRequest);
			serviceRequestRepository.save(sre);
		}catch(Exception e){
			e.printStackTrace();
			ResponseEntity<HttpStatus> response = new ResponseEntity<HttpStatus>(HttpStatus.UNPROCESSABLE_ENTITY);
			return response;
		}

		ResponseEntity<HttpStatus> response = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/sr/create", method = RequestMethod.POST, consumes = {"application/json"})
	public ResponseEntity<HttpStatus> create(@RequestBody(required = false) ServiceRequest serviceRequest)
	{
		LOGGER.info("\n\n Save Service Request: "+ serviceRequest.getId());

		ServiceRequestEntity sre = serviceRequestRepository.findServiceRequestByServiceId(serviceRequest.getServiceId());

		if (sre == null) {
			sre = new ServiceRequestEntity();
			sre.setServiceId(serviceRequest.getServiceId());
		}

		try {
			serviceRequestRepository.save(sre);
		}catch(Exception e){
			ResponseEntity<HttpStatus> response = new ResponseEntity<HttpStatus>(HttpStatus.UNPROCESSABLE_ENTITY);
			return response;
		}

		ResponseEntity<HttpStatus> response = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		return response;
	}

}