package com.camunda.poc.starter.usecase.registration.controller;

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

import java.util.*;

@Controller
public class RegistrationStartController {

	RuntimeService runtimeService;

	@Autowired
	public RegistrationStartController(RuntimeService runtimeService){
		this.runtimeService = runtimeService;
	}

	@RequestMapping(value = "/batch/start")
    public ResponseEntity<HttpStatus> start() {
		
		Map<String, Object> variables = new HashMap<>();
		
		RegistrationView registration1 = new RegistrationView();
		registration1.setTitle("Complete Accredation Application");
		registration1.setFirstName("Jim");
		registration1.setLastName("Dandy");
		registration1.setBusinessKey("I1072992");
		registration1.setCreated(new Date());
		registration1.setCreatedBy("djane");
		//setting a unique biz key for the process to associate the issue to the instance
		String uuid = UUID.randomUUID().toString();
		registration1.setRegistrationId(uuid);
		variables.put("registration", registration1);
		variables.put("email", "someemail@noreply.com");
		variables.put("newUser", false);
		runtimeService.startProcessInstanceByKey("registration-process-example",uuid,variables);
		
		RegistrationView registration2 = new RegistrationView();
		registration2.setTitle("Complete Accredation Application");
		registration2.setFirstName("Jim");
		registration2.setLastName("Dandy");
		registration2.setBusinessKey("I1072993");
		registration2.setCreated(new Date());
		registration2.setCreatedBy("djane");
		//setting a unique biz key for the process to associate the issue to the instance
		String uuid2 = UUID.randomUUID().toString();
		registration2.setRegistrationId(uuid2);
		variables.put("registration", registration2);
		variables.put("email", "someemail@noreply.com");
		variables.put("newUser", false);
		runtimeService.startProcessInstanceByKey("registration-process-example",uuid,variables);

		
		RegistrationView registration3 = new RegistrationView();
		registration3.setTitle("Integratoin Test Notifications");
		registration3.setFirstName("Jim");
		registration3.setLastName("Dandy");
		registration3.setBusinessKey("I1072992");
		registration3.setCreated(new Date());
		registration3.setCreatedBy("djane");
		//setting a unique biz key for the process to associate the issue to the instance
		String uuid3 = UUID.randomUUID().toString();
		registration3.setRegistrationId(uuid3);
		variables.put("registration", registration3);
		variables.put("email", "someemail@noreply.com");
		variables.put("newUser", false);
		runtimeService.startProcessInstanceByKey("registration-process-example",uuid,variables);

		RegistrationView registration4 = new RegistrationView();
		registration4.setTitle("Customer Tasks Overdue");
		registration4.setFirstName("Jim");
		registration4.setLastName("Dandy");
		registration4.setBusinessKey("I1072993");
		registration4.setCreated(new Date());
		registration4.setCreatedBy("djane");
		//setting a unique biz key for the process to associate the issue to the instance
		String uuid4 = UUID.randomUUID().toString();
		registration4.setRegistrationId(uuid4);
		variables.put("registration", registration4);
		variables.put("email", "someemail@noreply.com");
		variables.put("newUser", false);
		runtimeService.startProcessInstanceByKey("registration-process-example",uuid,variables);

		RegistrationView registration5 = new RegistrationView();
		registration5.setTitle("Accounts - Bill to contact update");
		registration5.setFirstName("Jim");
		registration5.setLastName("Dandy");
		registration5.setBusinessKey("I1072992");
		registration5.setCreated(new Date());
		registration5.setCreatedBy("djane");
		//setting a unique biz key for the process to associate the issue to the instance
		String uuid5 = UUID.randomUUID().toString();
		registration5.setRegistrationId(uuid5);
		variables.put("registration", registration5);
		variables.put("email", "someemail@noreply.com");
		runtimeService.startProcessInstanceByKey("registration-process-example",uuid,variables);

		RegistrationView registration6 = new RegistrationView();
		registration6.setTitle("Customer Tasks Overdue");
		registration6.setFirstName("Jim");
		registration6.setLastName("Dandy");
		registration6.setBusinessKey("I1072996");
		registration6.setCreated(new Date());
		registration6.setCreatedBy("djane");
		//setting a unique biz key for the process to associate the issue to the instance
		String uuid6 = UUID.randomUUID().toString();
		registration6.setRegistrationId(uuid6);
		variables.put("registration", registration6);
		variables.put("email", "someemail@noreply.com");
		runtimeService.startProcessInstanceByKey("registration-process-example",uuid,variables);

		
		//Return 200
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

	
}

	