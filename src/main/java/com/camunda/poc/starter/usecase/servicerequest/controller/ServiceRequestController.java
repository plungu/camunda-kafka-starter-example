package com.camunda.poc.starter.usecase.servicerequest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ServiceRequestController {

	@RequestMapping(value = "/sr")
	public String index() {
		return "app";
	}

}
