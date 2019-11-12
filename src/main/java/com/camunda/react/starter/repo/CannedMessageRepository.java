package com.camunda.react.starter.repo;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.camunda.react.starter.entity.CannedMessage;

public interface CannedMessageRepository extends PagingAndSortingRepository<CannedMessage, Long>{

}
