package com.camunda.poc.starter.usecase.servicerequest.repo;


import com.camunda.poc.starter.usecase.servicerequest.entity.CamundaServiceRequestTask;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CamundaSRTaskRepository extends PagingAndSortingRepository<CamundaServiceRequestTask, Long>{

}
