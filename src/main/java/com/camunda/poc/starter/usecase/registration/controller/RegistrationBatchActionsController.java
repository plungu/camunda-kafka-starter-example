package com.camunda.poc.starter.usecase.registration.controller;

import com.camunda.poc.starter.usecase.registration.entity.RegistrationView;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RegistrationBatchActionsController {

	TaskService taskService;

	@Autowired
	public RegistrationBatchActionsController (TaskService taskService) {
		this.taskService = taskService;
	}

	@RequestMapping(value="/complete", method= RequestMethod.POST, consumes = {"multipart/form-data", "application/x-www-form-urlencoded"})
	public String complete(  
				//this is just a reminder that I may need to store variables from the from in the process
			   @RequestParam(value = "role", required = true) String role,
			   @RequestParam(value = "User", required = false) String user,
			   @RequestParam(value = "priorities", required = false) List<String> priorities,
			   @RequestParam(value = "tasks", required = true) List<String> taskIds,			   
			   @RequestParam(value = "approved", required = true, defaultValue="false") Boolean approved)
	{
		
		System.out.println("User: "+user);
		System.out.println("Tasks: "+taskIds);
			
		int i = 0;
		for(String id : taskIds) {
			RegistrationView registration = null;
			Object issueObject = taskService.getVariable(id, "registration");
			if (issueObject != null/* && issueObject instanceof com.camunda.poc.roche.Issue*/) {
				registration = (RegistrationView) issueObject;
			}
			
			Map<String, Object> variables = new HashMap();
			
			System.out.println(id);
			
			variables.put("registration", registration);
			variables.put("approved", approved);
			taskService.complete(id, variables);
			i++;
		}
		
		return "redirect:batch?role="+role;
	}
	
}

	