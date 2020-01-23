package com.camunda.poc.starter.usecase.renewal.repo;

import java.util.Date;
import java.util.List;

import com.camunda.poc.starter.usecase.renewal.entity.Lease;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface LeaseRepository extends PagingAndSortingRepository<Lease, Long>{

	@Query("select l from lease l where l.end <= ?1 and l.renewalStarted = false and l.renewalCompleted = false")
	public List<Lease> findByEndDate(Date date);

	public List<Lease> findLeasesByShowDateOrderByShowDateAsc(
			@Param("showDate") Boolean showDate);
	
	@Query("select l from lease l where l.renewalStarted = true and l.renewalCompleted = false ORDER BY l.showDate DESC")
	public List<Lease> findStarted();

	public List<Lease> findLeasesByRenewalStartedAndRenewalCompletedOrderByRenewalStartedAsc(
			@Param("renewalStarted") Boolean renewalStarted,
			@Param("renewalCompleted") Boolean renewalCompleted);	

	public List<Lease> findLeasesByRenewalStartedAndRenewalCompletedOrderByWorkflowStateAsc(
			@Param("renewalStarted") Boolean renewalStarted,
			@Param("renewalCompleted") Boolean renewalCompleted);	
	
	@Query("select l from lease l where l.renewalStarted = true and l.renewalCompleted = false and l.workflowState like 'Confirm Renewal State' ORDER BY l.showDate DESC")
	public List<Lease> findByPriority();
	
	public List<Lease> findLeasesByRenewalStartedAndRenewalCompletedAndWorkflowStateOrderByShowDateAsc(
			@Param("renewalStarted") Boolean renewalStarted,
			@Param("renewalCompleted") Boolean renewalCompleted,
			@Param("workflowState") String workflowState);	

	public Lease findLeaseByProcessId(@Param("processId") Long processId);
}
