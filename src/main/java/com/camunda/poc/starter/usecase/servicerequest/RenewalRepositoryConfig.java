package com.camunda.poc.starter.usecase.servicerequest;

import com.camunda.poc.starter.usecase.servicerequest.entity.ServiceRequestEntity;
import com.camunda.poc.starter.usecase.servicerequest.kafka.integration.ServiceRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RenewalRepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(ServiceRequestEntity.class);
    }
}