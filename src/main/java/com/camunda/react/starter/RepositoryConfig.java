package com.camunda.react.starter;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.camunda.react.starter.entity.Lease;
import com.camunda.react.starter.entity.Message;
import com.camunda.react.starter.entity.Tenant;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Lease.class, Tenant.class, Message.class);
    }
}