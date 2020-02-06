package com.camunda.poc.starter.usecase.renewal.repo;

import java.util.Date;
import java.util.List;

import com.camunda.poc.starter.usecase.renewal.entity.Renewal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RenewalRepository extends PagingAndSortingRepository<Renewal, Long>{

	@Query("select l from renewal l where l.end <= ?1 and l.renewalStarted = false and l.renewalCompleted = false")
	public List<Renewal> findByEndDate(Date date);

	public List<Renewal> findRenewalsByShowDateOrderByShowDateAsc(
			@Param("showDate") Boolean showDate);

	@Query("select l from renewal l where l.renewalStarted = true and l.renewalCompleted = false ORDER BY l.showDate DESC")
	public List<Renewal> findStarted();

	public List<Renewal> findRenewalsByRenewalStartedAndRenewalCompletedOrderByRenewalStartedAsc(
			@Param("renewalStarted") Boolean renewalStarted,
			@Param("renewalCompleted") Boolean renewalCompleted);

	public List<Renewal> findRenewalsByRenewalStartedAndRenewalCompletedOrderByWorkflowStateAsc(
			@Param("renewalStarted") Boolean renewalStarted,
			@Param("renewalCompleted") Boolean renewalCompleted);

	@Query("select l from renewal l where l.renewalStarted = true and l.renewalCompleted = false and l.workflowState like 'Confirm Renewal State' ORDER BY l.showDate DESC")
	public List<Renewal> findByPriority();

	public List<Renewal> findRenewalsByRenewalStartedAndRenewalCompletedAndWorkflowStateOrderByShowDateAsc(
			@Param("renewalStarted") Boolean renewalStarted,
			@Param("renewalCompleted") Boolean renewalCompleted,
			@Param("workflowState") String workflowState);

	public Renewal findRenewalByBusinessKey(@Param("businessKey") String businessKey);
}
