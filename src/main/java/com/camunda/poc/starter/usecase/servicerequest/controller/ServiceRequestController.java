package com.camunda.poc.starter.usecase.servicerequest.controller;

import com.camunda.poc.starter.usecase.servicerequest.kafka.integration.ServiceRequest;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Profile("mspoc")
@RestController
public class ServiceRequestController {

	private final Logger LOGGER = Logger.getLogger(Class.class.getName());

	public ServiceRequestController() {
	}

	private RuntimeService runtimeService;
	private TaskService taskService;

	@Autowired
	public ServiceRequestController(RuntimeService runtimeService,
									TaskService taskService) {
		this.runtimeService = runtimeService;
		this.taskService = taskService;
	}

	@RequestMapping(value = "/sr")
	public String index() {
		return "app";
	}


	@RequestMapping(value = "/sr/start", method = RequestMethod.POST, consumes = {"application/json"})
	public ResponseEntity<HttpStatus> start(@RequestBody(required = false) ServiceRequest serviceRequest)
	{
		LOGGER.info("\n\n Start Service Request"+ serviceRequest);

		Map<String, Object> variables = new HashMap();
		variables.put("approved", false);
		variables.put("serviceRequest", serviceRequest);

		runtimeService.correlateMessage("start-service-request", serviceRequest.getServiceId(),variables);
		ResponseEntity<HttpStatus> response = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/sr/update/rejected", method = RequestMethod.POST, consumes = {"application/json"})
	public ResponseEntity<HttpStatus> update(@RequestBody(required = false) ServiceRequest serviceRequest)
	{
		LOGGER.info("\n\n Update Rejected Service Request"+ serviceRequest);

		Map<String, Object> variables = new HashMap();
		variables.put("approved", false);
		variables.put("serviceRequest", serviceRequest);

		runtimeService.correlateMessage("service-owner-sr-rejection", serviceRequest.getServiceId(),variables);
		ResponseEntity<HttpStatus> response = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/sr/save", method = RequestMethod.POST, consumes = {"application/json"})
	public ResponseEntity<HttpStatus> save(@RequestBody(required = false) ServiceRequest serviceRequest)
	{

		LOGGER.info("\n\n  Save Service Request"+ serviceRequest);

		ResponseEntity<HttpStatus> response = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/sr/task/approve", method = RequestMethod.POST, consumes = {"application/json"})
	public ResponseEntity<HttpStatus> approve(@RequestBody(required = false) ServiceRequest serviceRequest)
	{

		LOGGER.info("\n\n  Received Service Request Task with Approval: "+ serviceRequest.getServiceId());

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(serviceRequest.getServiceId().trim()).singleResult();

		LOGGER.info("\n\n  Complete Service Request Task with Approval"+ task.getName());

		taskService.complete(task.getId(), Variables.putValue("approved", true));

		ResponseEntity<HttpStatus> response = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/sr/task/reject", method = RequestMethod.POST, consumes = {"application/json"})
	public ResponseEntity<HttpStatus> Reject(@RequestBody(required = true) ServiceRequest serviceRequest)
	{
		LOGGER.info("\n\n  Received Service Request Task with Reject: "+ serviceRequest.getServiceId());

		Task task = taskService.createTaskQuery().processInstanceBusinessKey(serviceRequest.getServiceId().trim()).singleResult();

		LOGGER.info("\n\n  Complete Service Request Task with rejection"+ task.getName());

		taskService.complete(task.getId(), Variables.putValue("approved", false));

		ResponseEntity<HttpStatus> response = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		return response;
	}



}