package com.camunda.react.starter.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.init;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.processEngine;

import com.camunda.react.starter.LeaseRenewalTest;
import com.camunda.react.starter.WorkflowUtil;
import com.camunda.react.starter.bpm.LeaseRenewalTestBase;
import com.camunda.react.starter.entity.Lease;
import com.camunda.react.starter.entity.Tenant;
import com.camunda.react.starter.repo.LeaseRepository;
import com.camunda.react.starter.repo.MessageRepository;
import com.camunda.react.starter.repo.TenantRepository;
import com.camunda.react.starter.service.LeaseCleanupServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {LeaseRenewalTest.class})
public class LeaseCleanupServiceTest extends LeaseRenewalTestBase{

    @Autowired
    private LeaseRepository leaseRepository;
        
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private MessageRepository messageRepository;
    

    @ClassRule
    @Rule
    public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

    //private static final String PROCESS_DEFINITION_KEY = "wit-nlp-integration";

    static {
      LogFactory.useSlf4jLogging(); // MyBatis
    }

    @Before
    public void setup() {
      init(rule.getProcessEngine());

    	// Setup the workflow
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

    	//Setup the data
    	String property = "1180 Atlantis Ave, Lafayette Colorado CO 80026";
    	
        Lease lease = new Lease(Calendar.getInstance().getTime(), WorkflowUtil.getKickOffDate(100), property);
        lease.setRenewalStarted(true);
        lease.setProcessId(processInstance.getId());
        leaseRepository.save(lease);

        Tenant tenant = new Tenant("Paul Lungu", "lungu77@gmail.com", property, lease);
        tenantRepository.save(tenant);

		
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
    
	@Test
    @Deployment(resources = { 
   	     "processes/leaseRenewalProcess.bpmn" 
   		})
    public void leaseCleanupTest() throws Exception {
        
		LeaseCleanupServiceImpl service = new LeaseCleanupServiceImpl();
		service.setHistoryService(processEngine().getHistoryService());
		service.setLeaseRepository(leaseRepository);
		service.setMessageRepository(messageRepository);
		service.clean();
		
		//TODO: Need to get the correct leaseReposoitory for the test context
    	List<Lease> leases = leaseRepository.findStarted();
    	Assert.assertTrue(leases.get(0).isRenewalCompleted());
    }
    
}
