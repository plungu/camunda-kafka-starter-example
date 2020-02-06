package com.camunda.poc.starter.usecase.renewal.repo;


import com.camunda.poc.starter.usecase.renewal.entity.CamundaTask;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CamundaTaskRepository extends PagingAndSortingRepository<CamundaTask, Long>{}
