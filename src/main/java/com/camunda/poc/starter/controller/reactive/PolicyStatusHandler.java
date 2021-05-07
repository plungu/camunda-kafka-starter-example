package com.camunda.poc.starter.controller.reactive;

import com.camunda.poc.starter.entity.PolicyEntity;
import com.camunda.poc.starter.entity.ServiceRequestEntity;
import com.camunda.poc.starter.repo.ReactivePolicyRepositoryImpl;
import com.camunda.poc.starter.repo.ReactiveServiceRequestRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Profile("triage")
@RestController
@RequestMapping
public class PolicyStatusHandler {

    @Autowired
    ReactivePolicyRepositoryImpl policyRepository;

    @GetMapping("/policy/{id}")
    public Mono<PolicyEntity> getPolicy(@PathVariable String id)  {
        return policyRepository.findPolicyById(id);
    }

    @GetMapping("/policy/creditcheck/started")
    public Flux<PolicyEntity> getCreditCheckStarted()  {
        return policyRepository.findPolicyByCreditCheckStarted(true);
    }

}
