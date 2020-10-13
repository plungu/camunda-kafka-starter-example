package com.camunda.poc.starter;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@EnableProcessApplication("spring-boot-starter")
@EnableAdminServer
public class CamundaApplication {

	public static void main(String... args) {
	    SpringApplication.run(CamundaApplication.class, args);
	}

}