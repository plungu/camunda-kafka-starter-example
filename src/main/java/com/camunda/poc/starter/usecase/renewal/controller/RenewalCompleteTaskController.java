package com.camunda.poc.starter.usecase.renewal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camunda.poc.starter.usecase.renewal.RenewalUtil;
import com.camunda.poc.starter.usecase.renewal.repo.RenewalRepository;
import com.camunda.poc.starter.usecase.renewal.repo.MessageRepository;
import com.camunda.poc.starter.usecase.renewal.repo.TenantRepository;

@RestController
public class RenewalCompleteTaskController {
	public static Logger log = Logger.getLogger(RenewalCompleteTaskController.class.getName());

	private TaskService taskService;

	public RenewalCompleteTaskController(TaskService taskService) {
		this.taskService = taskService;
	}

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
     * @param bizKey
     * @param renewalId
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
			   @RequestParam(value = "processId") String bizKey,
			   @RequestParam(value = "renewalId", required = false) String renewalId)
    {
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);

		log.info("\n\n Biz Key: "+bizKey);
		List<Task> tasks = RenewalUtil.queryTasksByBizKey(taskService, bizKey);
    	if (tasks.isEmpty()){
    		//TODO: send message to property manager tenant sent a message but has no tasks.
	    	log.info("\n\n No tasks found for :"+bizKey);
    	}
    	
		Task task = tasks.get(0);
		log.info("\n\n Completing Task: "+task.getName());
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
		
		taskService.complete(task.getId(), variables);
    	
		return re;
    }
    	
}
