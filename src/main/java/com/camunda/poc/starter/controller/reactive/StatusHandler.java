package com.camunda.poc.starter.controller.reactive;

import com.camunda.poc.starter.entity.ServiceRequestEntity;
import com.camunda.poc.starter.repo.ReactiveServiceRequestRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Profile("service-request")
@RestController
@RequestMapping
public class StatusHandler {

    @Autowired
    ReactiveServiceRequestRepositoryImpl serviceRequestRepository;

    @GetMapping("/sr/{id}")
    public Mono<ServiceRequestEntity> getServiceRequest(@PathVariable String id)  {
        return serviceRequestRepository.findServiceRequestByServiceId(id);
    }

    @GetMapping("/sr/rejected")
    public Flux<ServiceRequestEntity> getRejectedServiceRequests()  {
        return serviceRequestRepository.findServiceRequestEntitiesByRejectedAndStarted(true, true);
    }

    @GetMapping("/sr/approved")
    public Flux<ServiceRequestEntity> getApprovedServiceRequests()  {
        return serviceRequestRepository.findServiceRequestEntitiesByApprovedAndStarted(true, true);
    }
}
