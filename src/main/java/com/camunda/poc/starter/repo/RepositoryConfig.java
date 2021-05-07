package com.camunda.poc.starter.repo;

import com.camunda.poc.starter.entity.PolicyEntity;
import com.camunda.poc.starter.entity.ServiceRequestEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

    @Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(PolicyEntity.class);
    }

}