package com.camunda.react.starter.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camunda.react.starter.WorkflowUtil;
import com.camunda.react.starter.repo.LeaseRepository;
import com.camunda.react.starter.repo.MessageRepository;
import com.camunda.react.starter.repo.TenantRepository;

@Profile("rest")
@RestController
public class CompleteTaskController {
	public static Logger log = Logger.getLogger(CompleteTaskController.class.getName());

    @Autowired RuntimeService runtimeService;

    @Autowired TaskService taskService;
    
    @Autowired HistoryService historyService;
    
    @Autowired MessageRepository messageRepository;
    
    @Autowired LeaseRepository leaseRepository;

    @Autowired TenantRepository tenantRepository;

    /**
     * Controller Request Mapping that processes request from the ReactUI 
     * Updates the renewal state
     * 
     * @param confirm
     * @param renewing
     * @param signed
     * @param text
     * @param subject
     * @param gracePeriod
     * @param processId
     * @param leaseId
     * @return
     */
    @RequestMapping(value="/message", method= RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<HttpStatus> sendMessage(     								   
    		   @RequestParam(value = "confirm", required = false) Boolean confirm,
    		   @RequestParam(value = "renewing", required = false) Boolean renewing,
    		   @RequestParam(value = "signed", required = false) Boolean signed,
			   @RequestParam(value = "text") String text,
			   @RequestParam(value = "subject") String subject,
			   @RequestParam(value = "gracePeriod", required = false) Integer gracePeriod,
			   @RequestParam(value = "processId") String processId,
			   @RequestParam(value = "leaseId", required = false) String leaseId)			   
    {
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		List<Task> tasks = WorkflowUtil.queryTasksById(taskService, processId);
    	if (tasks.isEmpty()){
    		//TODO: send message to property manager tenant sent a message but has no tasks.
	    	log.fine("[X] No tasks found for :"+processId);
    	}
    	
		Task task = tasks.get(0);
		log.fine("Completing Task: "+task.getName());		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("subject", subject);
		variables.put("message", text);
		variables.put("renewalConfirmed", confirm);
		if(gracePeriod != null){
			if (gracePeriod == 5)
				variables.put("gracePeriod", "0 0 0/5 * * ?");
			else if (gracePeriod == 3)
				variables.put("gracePeriod", "0 0 0/3 * * ?");
			else if (gracePeriod == 2)
				variables.put("gracePeriod", "0 0 0/2 * * ?");		
			else if (gracePeriod == 1)
				variables.put("gracePeriod", "0 0 0/1 * * ?");
		}
		//TODO: use REST to complete task
		taskService.complete(task.getId(), variables);
    	
		return re;
    }
    	
}
