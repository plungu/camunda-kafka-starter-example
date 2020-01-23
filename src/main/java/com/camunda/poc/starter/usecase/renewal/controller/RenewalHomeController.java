package com.camunda.poc.starter.usecase.renewal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RenewalHomeController {

	@RequestMapping(value = "/renewals")
	public String index() {
		return "app";
	}

}
