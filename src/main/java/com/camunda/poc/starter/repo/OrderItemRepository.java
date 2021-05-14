package com.camunda.poc.starter.repo;

import com.camunda.poc.starter.entity.OrderItem;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Profile("ordering")
public interface OrderItemRepository extends PagingAndSortingRepository<OrderItem, Long>{

//	@Query("select l from renewal l where l.end <= ?1 and l.renewalStarted = false and l.renewalCompleted = false")
//	public List<Renewal> findByEndDate(Date date);

	List<OrderItem> findOrderItemsByPmiCode(@Param("pmiCode") String pmiCode);
	List<OrderItem> findOrderItemByQuantityIsLessThan(@Param("quantity") Integer quantity);

}
