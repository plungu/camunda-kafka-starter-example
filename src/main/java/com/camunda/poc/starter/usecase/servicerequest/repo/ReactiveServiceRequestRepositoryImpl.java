package com.camunda.poc.starter.usecase.servicerequest.repo;

import com.camunda.poc.starter.usecase.servicerequest.entity.ServiceRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class ReactiveServiceRequestRepositoryImpl implements ReactiveServiceRequestRepository{

	@Autowired
	ServiceRequestRepository serviceRequestRepository;

	public Flux<ServiceRequestEntity> findServiceRequestEntitiesByApprovedAndStarted(Boolean approved, Boolean started){
		List<ServiceRequestEntity> sres = serviceRequestRepository
				.findServiceRequestEntitiesByApprovedAndStarted(approved, started);
		return Flux.fromIterable(sres);
	}

	public Flux<ServiceRequestEntity> findServiceRequestEntitiesByRejectedAndStarted(Boolean approved, Boolean started){
		List<ServiceRequestEntity> sres = serviceRequestRepository
				.findServiceRequestEntitiesByRejectedAndStarted(approved, started);
		return Flux.fromIterable(sres);
	}

	public Mono<ServiceRequestEntity> findServiceRequestByServiceId(String serviceId){
		ServiceRequestEntity sre = serviceRequestRepository.findServiceRequestByServiceId(serviceId);
		return Mono.just(sre);
	}

}
