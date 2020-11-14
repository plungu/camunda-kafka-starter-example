package com.camunda.poc.starter;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableProcessApplication("spring-boot-starter")
//@EnableAdminServer
public class CamundaApplication {

	public static void main(String... args) {
	    SpringApplication.run(CamundaApplication.class, args);
	}

}