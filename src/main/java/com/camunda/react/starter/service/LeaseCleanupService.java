package com.camunda.react.starter.service;

import javax.transaction.Transactional;

public interface LeaseCleanupService {

	@Transactional
	public void clean();
}
