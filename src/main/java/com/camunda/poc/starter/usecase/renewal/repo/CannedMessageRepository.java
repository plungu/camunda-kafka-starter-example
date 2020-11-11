package com.camunda.poc.starter.usecase.renewal.repo;


import com.camunda.poc.starter.usecase.renewal.entity.CannedMessage;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CannedMessageRepository extends PagingAndSortingRepository<CannedMessage, Long>{

}
