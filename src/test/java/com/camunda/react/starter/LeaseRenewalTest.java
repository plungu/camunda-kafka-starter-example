package com.camunda.react.starter;


import java.text.ParseException;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LeaseRenewalTest{	

	public static void main(String[] args) throws ParseException {
        SpringApplication.run(LeaseRenewalTest.class, args);
	}
	
	@Bean
	public DataSource database() {
	    return DataSourceBuilder.create()
	        .url("jdbc:postgresql://127.0.0.1:5432/lease_renewal?characterEncoding=UTF-8")
	        .username("dev_role")
	        .password("Budwe1ser")
	        .driverClassName("org.postgresql.Driver")
	        .build();
	}
	
}