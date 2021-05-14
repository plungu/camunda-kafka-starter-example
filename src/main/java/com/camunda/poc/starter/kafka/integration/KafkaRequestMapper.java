package com.camunda.poc.starter.kafka.integration;

import com.camunda.poc.starter.entity.Order;
import com.camunda.poc.starter.entity.ServiceRequestEntity;
import org.springframework.context.annotation.Profile;

@Profile("integration")
public class KafkaRequestMapper {
    private long id;

    public KafkaRequestMapper(){}

}
