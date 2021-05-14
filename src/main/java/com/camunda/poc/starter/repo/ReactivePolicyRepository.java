package com.camunda.poc.starter.repo;

import com.camunda.poc.starter.entity.PolicyEntity;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Profile("policy")
public interface ReactivePolicyRepository {

	Flux<PolicyEntity> findPolicyByCreditCheckStarted(Boolean started);

	Mono<PolicyEntity> findPolicyById(String Id);

}