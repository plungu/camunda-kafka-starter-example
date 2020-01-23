package com.camunda.poc.starter.usecase.registration.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.camunda.poc.starter.usecase.registration.entity.RegistrationView;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationGetController {
	RuntimeService runtimeService;
	TaskService taskService;

	@Autowired
	public RegistrationGetController(RuntimeService runtimeService, TaskService taskService) {
		this.runtimeService = runtimeService;
		this.taskService = taskService;
	}

	@RequestMapping(value = "/batch")
    public String index(@RequestParam(value="role", required=false, defaultValue="Manager") String role, Model model) {
		
		System.out.println("Role: "+role);
		
		//for now just get the active tasks
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(role).list();
		
		System.out.println("Tasks: "+tasks);
		
		//set the issues into the UI model and associate a task with the issue
		//so the task can be completed later
		List<RegistrationView> registrations = new ArrayList<RegistrationView>();
		for (Task task : tasks)
		{
			RegistrationView registration = (RegistrationView)runtimeService.getVariable(task.getExecutionId(), "registration");
			registration.setTaskId(task.getId());
			registration.setAddedOrgName("Johns Hopkins");
			registration.setAddedOrgPartyNumber("243324532ffsdw3");
			registration.setNewIuName("LuLuName");
			registration.setNewIuNumber("34543sdsfssdf");
			registration.setNewIuPrimAccContEmail("jim@johnshopkins.org");
			registration.setNewIuPrimAccContPhone("3035679888");
			registration.setNewIuPrimAccContFname("Jim");
			registration.setNewIuPrimAccContLname("Dandy");
			registration.setNewIuPrimOrgName("Jimmy Johns");
			registration.setNewIuPrimOrgPartyNumber("34533000");
			registrations.add(registration);
		}
		//set the objects as lists into the ui model
		//it may be better to store the task as part of the issue
		//for now I only store the task Id to use to complete the task later
        model.addAttribute("registrations", registrations);
        model.addAttribute("tasks", tasks);
        model.addAttribute("role", role);
        
        return "batch";
    }
}

	