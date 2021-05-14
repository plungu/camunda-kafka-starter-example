package com.camunda.poc.starter.repo;

import com.camunda.poc.starter.entity.PolicyEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Profile("policy")
public interface PolicyRepository extends PagingAndSortingRepository<PolicyEntity, Long>{

//	@Query("select l from renewal l where l.end <= ?1 and l.renewalStarted = false and l.renewalCompleted = false")
//	public List<Renewal> findByEndDate(Date date);

	PolicyEntity findPolicyEntitiesById(@Param("id") String id);

	PolicyEntity findPolicyEntitiesByCoPolicyNo(@Param("coPolicyNo") String qrCode);

	List<PolicyEntity> findPolicyEntitiesByCreditCheckStarted(@Param("isCreditCheckStarted") Boolean isCreditCheckStarted);

}
