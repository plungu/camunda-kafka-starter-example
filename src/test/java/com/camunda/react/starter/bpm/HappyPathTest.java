package com.camunda.react.starter.bpm;

import static org.junit.Assert.*;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import com.camunda.react.starter.WorkflowUtil;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;

public class HappyPathTest extends LeaseRenewalTestBase{

	  @ClassRule
	  @Rule
	  public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();


	  static {
	    LogFactory.useSlf4jLogging(); // MyBatis
	  }

	  @Before
	  public void setup() {
	    init(rule.getProcessEngine());
	  }
	  
	@Test
	@Deployment(resources = { 
     "processes/leaseRenewalProcess.bpmn" 
	})
	public void happyPath(){
			    	
		/*
		 * Get the runtime service
		 * Start the process
		 * Set the initial params
		 */
		RuntimeService runtimeService = processEngine().getRuntimeService();
	
		Date leaseExpirationDate = getLeaseExpirationDate(100);
		
		List<String> emails = new ArrayList<String>();
		emails.add("lungu77@gmail.com");		
				
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("leaseExpirationDate", leaseExpirationDate);
		variables.put("leaseExpirationBufferDays", 50);
		variables.put("gracePeriod", "0/5 * * * * ?");
		variables.put("message", "Do you want to renew your lease?");
		variables.put("finalNoticeSent", false);
		variables.put("renewalConfirmed", true);
		variables.put("tenants", emails);
		variables.put("recipiants", "lungu77@gmail.com");
		variables.put("gracePeriodSettings", getGracePeriodSetting());

		ProcessInstance processInstance =
		  runtimeService.startProcessInstanceByKey("leaseRenewal", variables);
		
		//Task tests confirm the workflow is going through correct path
		assertTrue(WorkflowUtil.checkTaskComplete(processEngine().getHistoryService(), processInstance.getId(), "startEvent"));
		getRemainingDaysTaskTest();
		assertTrue("getRemainingDays", WorkflowUtil.checkTaskComplete(processEngine().getHistoryService(), processInstance.getId(), "getRemainingDays"));
		assertTrue(WorkflowUtil.checkTaskComplete(processEngine().getHistoryService(), processInstance.getId(), "noticeDecision"));
		assertTrue(WorkflowUtil.checkTaskComplete(processEngine().getHistoryService(), processInstance.getId(), "sendInitalTenantMessage"));
		awaitTenantReplyTaskTest("lungu77@gmail.com");
		assertTrue(WorkflowUtil.checkTaskComplete(processEngine().getHistoryService(), processInstance.getId(), "awaitTenantReply"));
		assertTrue(WorkflowUtil.checkTaskComplete(processEngine().getHistoryService(), processInstance.getId(), "notifyPropertyManager"));
		confrimRenewalStateTaskTest();
		assertTrue(WorkflowUtil.checkTaskComplete(processEngine().getHistoryService(), processInstance.getId(), "confrimRenewalState"));
		assertTrue(WorkflowUtil.checkTaskComplete(processEngine().getHistoryService(), processInstance.getId(), "renewalStateDecision"));
		assertTrue(WorkflowUtil.checkTaskComplete(processEngine().getHistoryService(), processInstance.getId(), "sendTenantConfirmation"));
		assertTrue(WorkflowUtil.checkTaskComplete(processEngine().getHistoryService(), processInstance.getId(), "end"));
	}
	
}
