package com.camunda.poc.starter.usecase.servicerequest;

import com.camunda.poc.starter.usecase.servicerequest.entity.ServiceRequestEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RenewalRepositoryConfig implements RepositoryRestConfigurer {

    @Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(ServiceRequestEntity.class);
    }

}