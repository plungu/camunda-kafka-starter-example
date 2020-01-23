package com.camunda.poc.starter.usecase.renewal;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.camunda.poc.starter.usecase.renewal.entity.Lease;
import com.camunda.poc.starter.usecase.renewal.entity.Message;
import com.camunda.poc.starter.usecase.renewal.entity.Tenant;

@Configuration
public class RenewalRepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Lease.class, Tenant.class, Message.class);
    }
}