package com.camunda.react.starter.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.camunda.react.starter.entity.Lease;
import com.camunda.react.starter.entity.Message;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long>{

	@Query("select m from message m where m.lease = ?1")
	public List<Message> findAllByLease(Lease lease);
}
