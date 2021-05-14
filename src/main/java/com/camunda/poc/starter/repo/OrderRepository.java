package com.camunda.poc.starter.repo;

import com.camunda.poc.starter.entity.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Profile("ordering")
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>{

//	@Query("select l from renewal l where l.end <= ?1 and l.renewalStarted = false and l.renewalCompleted = false")
//	public List<Renewal> findByEndDate(Date date);

	Order findOrderByOrderKey(@Param("orderKey") String orderKey);

	List<Order> findOrderByEmailAndStarted(@Param("email") String email, @Param("started") Boolean started);

	List<Order> findOrderByStarted(@Param("started") Boolean started);

}
