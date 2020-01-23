package com.camunda.poc.starter.usecase.renewal.repo;

import java.util.List;

import com.camunda.poc.starter.usecase.renewal.entity.Tenant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface TenantRepository extends PagingAndSortingRepository<Tenant, Long>{

	@Query("select u from tenant u where u.email = ?1")
	public Tenant findByEmail(String email);

	public List<Tenant> findTenantsByUnitSlug(@Param("unitSlug") String unitSlug);

}
