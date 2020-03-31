package com.camunda.poc.starter.usecase.renewal.controller;

import java.util.Optional;

//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.camunda.bpm.engine.task.Task;
import com.camunda.poc.starter.usecase.renewal.AppConfigProperties;
import com.camunda.poc.starter.usecase.renewal.RenewalUtil;
import com.camunda.poc.starter.usecase.renewal.repo.CamundaRenewalTaskRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.rest.webmvc.RepositoryRestController;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.hateoas.ExposesResourceFor;
//import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.camunda.react.starter.WorkflowUtil;
import com.camunda.poc.starter.usecase.renewal.entity.Renewal;
import com.camunda.poc.starter.usecase.renewal.repo.RenewalRepository;

@RestController
public class RenewalActionsController {

	RenewalRepository renewalRepository;
	CamundaRenewalTaskRepository camundaRenewalTaskRepository;
	RuntimeService runtimeService;
	TaskService taskService;
	AppConfigProperties config;

	@Autowired
	public RenewalActionsController(RenewalRepository renewalRepository,
									CamundaRenewalTaskRepository camundaRenewalTaskRepository,
									RuntimeService runtimeService,
									TaskService taskService,
									AppConfigProperties config){

		this.renewalRepository = renewalRepository;
		this.runtimeService = runtimeService;
		this.taskService = taskService;
		this.config = config;
	}

	@RequestMapping(value="/renewal/start", method=RequestMethod.GET)
	public ResponseEntity<HttpStatus> start()
	{
		ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);

		ProcessInstance processInstance = RenewalUtil.startRenewal( renewalRepository,
																	camundaRenewalTaskRepository,
																	runtimeService,
																	config);
		if(processInstance==null)
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);

		return re;
	}

	@RequestMapping(value="/updateNote", method=RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<HttpStatus> updateNote(     								   
    		@RequestParam(value = "renewalId") String renewalId,
    		@RequestParam(value = "note") String note)
    {
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		
    	Long id = Long.valueOf(renewalId);
    	Optional<Renewal> renewal = renewalRepository.findById(id);
    	if(renewal==null)
    		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    	
    	renewal.get().setNote(note);
    	renewalRepository.save(renewal.get());
    	
		return re;
    }

    @RequestMapping(value="/signed", method=RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<HttpStatus> signed(     								   
    		@RequestParam(value = "renewalId") String renewalId,
    		@RequestParam(value = "signed") Boolean signed)
    {
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		
    	Long id = Long.valueOf(renewalId);
    	Optional<Renewal> renewal = renewalRepository.findById(id);
    	if(renewal==null)
    		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    	
    	renewal.get().setSigned(signed);
    	renewalRepository.save(renewal.get());
    	
		return re;
    }

    @RequestMapping(value="/renewing", method=RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<HttpStatus> renewing(     								   
    		@RequestParam(value = "renewalId") String renewalId,
    		@RequestParam(value = "renewing", required = false) Boolean renewing)
    {
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		
    	Long id = Long.valueOf(renewalId);
    	Optional<Renewal> renewal = renewalRepository.findById(id);
    	if(renewal==null)
    		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    	if(renewing != null)
    		renewal.get().setRenewing(renewing);
    	
    	renewalRepository.save(renewal.get());
    	
		return re;
    }
}
