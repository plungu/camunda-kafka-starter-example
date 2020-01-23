package com.camunda.poc.starter.usecase.renewal.repo;

import java.util.List;

import com.camunda.poc.starter.usecase.renewal.entity.Lease;
import com.camunda.poc.starter.usecase.renewal.entity.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long>{

	@Query("select m from message m where m.lease = ?1")
	public List<Message> findAllByLease(Lease lease);
}
