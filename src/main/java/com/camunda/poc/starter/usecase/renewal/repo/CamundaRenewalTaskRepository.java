package com.camunda.poc.starter.usecase.renewal.repo;


import com.camunda.poc.starter.usecase.renewal.entity.CamundaRenewalTask;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CamundaRenewalTaskRepository extends PagingAndSortingRepository<CamundaRenewalTask, Long>{


}
