package com.camunda.poc.starter.usecase.renewal;

import javax.transaction.Transactional;

public interface RenewalCleanupService {

	@Transactional
	public void clean();
}
