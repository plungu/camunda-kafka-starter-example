package com.camunda.poc.starter.usecase.servicerequest.repo;

import com.camunda.poc.starter.usecase.servicerequest.entity.ServiceRequestEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRequestRepository extends PagingAndSortingRepository<ServiceRequestEntity, Long>{

//	@Query("select l from renewal l where l.end <= ?1 and l.renewalStarted = false and l.renewalCompleted = false")
//	public List<Renewal> findByEndDate(Date date);
//
//	public List<Renewal> findRenewalsByShowDateOrderByShowDateAsc(
//            @Param("showDate") Boolean showDate);
//
//	@Query("select l from renewal l where l.renewalStarted = true and l.renewalCompleted = false ORDER BY l.showDate DESC")
//	public List<Renewal> findStarted();
//
	public List<ServiceRequestEntity> findServiceRequestEntitiesByApprovedAndStarted(
            @Param("approved") Boolean approved, @Param("started") Boolean started);

	public List<ServiceRequestEntity> findServiceRequestEntitiesByRejectedAndStarted(
			@Param("rejected") Boolean rejected, @Param("started") Boolean started);

//	public List<Renewal> findRenewalsByRenewalStartedAndRenewalCompletedOrderByWorkflowStateAsc(
//            @Param("renewalStarted") Boolean renewalStarted,
//            @Param("renewalCompleted") Boolean renewalCompleted);
//
//	@Query("select l from renewal l where l.renewalStarted = true and l.renewalCompleted = false and l.workflowState like 'Confirm Renewal State' ORDER BY l.showDate DESC")
//	public List<Renewal> findByPriority();
//
//	public List<Renewal> findRenewalsByRenewalStartedAndRenewalCompletedAndWorkflowStateOrderByShowDateAsc(
//            @Param("renewalStarted") Boolean renewalStarted,
//            @Param("renewalCompleted") Boolean renewalCompleted,
//            @Param("workflowState") String workflowState);

//	public ServiceRequest findServiceRequestByBusinessKey(@Param("businessKey") String businessKey);

	public ServiceRequestEntity findServiceRequestByServiceId(@Param("serviceId") String serviceId);

}
