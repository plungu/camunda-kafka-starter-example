package com.camunda.poc.starter.repo;


import com.camunda.poc.starter.entity.CamundaServiceRequestTask;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CamundaSRTaskRepository extends PagingAndSortingRepository<CamundaServiceRequestTask, Long>{

}
