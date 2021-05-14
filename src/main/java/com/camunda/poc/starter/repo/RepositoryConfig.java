package com.camunda.poc.starter.repo;

import com.camunda.poc.starter.entity.Contact;
import com.camunda.poc.starter.entity.Order;
import com.camunda.poc.starter.entity.OrderItem;
import com.camunda.poc.starter.entity.StockItem;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Profile("ordering")
@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

  @Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
    config.exposeIdsFor(Order.class, OrderItem.class, StockItem.class, Contact.class);
  }

}
