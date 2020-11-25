package com.camunda.poc.starter.usecase.servicerequest.repo;

import com.camunda.poc.starter.usecase.servicerequest.entity.ServiceRequestEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveServiceRequestRepository {

	Flux<ServiceRequestEntity> findServiceRequestEntitiesByApprovedAndStarted(Boolean approved, Boolean started);

	Flux<ServiceRequestEntity> findServiceRequestEntitiesByRejectedAndStarted(Boolean rejected, Boolean started);

	Mono<ServiceRequestEntity> findServiceRequestByServiceId(String serviceId);

}