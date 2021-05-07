package com.camunda.poc.starter.repo;

import com.camunda.poc.starter.entity.PolicyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class ReactivePolicyRepositoryImpl implements ReactivePolicyRepository{

	@Autowired
	PolicyRepository policyRepository;

	public Flux<PolicyEntity> findPolicyByCreditCheckStarted(Boolean started){
		List<PolicyEntity> p = policyRepository
				.findPolicyEntitiesByCreditCheckStarted(started);
		return Flux.fromIterable(p);
	}


	public Mono<PolicyEntity> findPolicyById(String id){
//		PolicyEntity p = policyRepository.findPolicyEntitiesById(id);
		PolicyEntity p = policyRepository.findPolicyEntitiesByQrCode(id);
		return Mono.just(p);
	}

}
