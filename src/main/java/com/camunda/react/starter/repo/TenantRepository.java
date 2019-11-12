package com.camunda.react.starter.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.camunda.react.starter.entity.Tenant;

public interface TenantRepository extends PagingAndSortingRepository<Tenant, Long>{

	@Query("select u from tenant u where u.email = ?1")
	public Tenant findByEmail(String email);

	public List<Tenant> findTenantsByUnitSlug(@Param("unitSlug") String unitSlug);

}
