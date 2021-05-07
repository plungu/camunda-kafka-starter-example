package com.camunda.poc.starter.repo;

import com.camunda.poc.starter.entity.PolicyEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactivePolicyRepository {

	Flux<PolicyEntity> findPolicyByCreditCheckStarted(Boolean started);

	Mono<PolicyEntity> findPolicyById(String Id);

}