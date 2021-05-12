package com.camunda.poc.starter.bpm;

import com.camunda.poc.starter.entity.PolicyEntity;
import com.camunda.poc.starter.repo.PolicyRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


/**
 * This is an easy adapter implementation 
 * illustrating how a Java Delegate can be used 
 * from within a BPMN 2.0 Service Task.
 */
@Component("uiCacheUpdateDelegate")
public class UiCacheUpdateDelegate implements JavaDelegate {
 
  private final Logger LOGGER = Logger.getLogger(UiCacheUpdateDelegate.class.getName());

  PolicyRepository policyRepository;

  @Autowired
  public void UiCacheUpdateDelegate(PolicyRepository policyRepository){
    this.policyRepository = policyRepository;
  }

  public void execute(DelegateExecution execution) throws Exception {
    
    LOGGER.info("\n\n  ... LoggerDelegate invoked by "
            + "processDefinitionId=" + execution.getProcessDefinitionId()
            + ", activtyId=" + execution.getCurrentActivityId()
            + ", activtyName='" + execution.getCurrentActivityName() + "'"
            + ", processInstanceId=" + execution.getProcessInstanceId()
            + ", businessKey=" + execution.getProcessBusinessKey()
            + ", executionId=" + execution.getId()
            + " \n\n");

    PolicyEntity policy = policyRepository.findPolicyEntitiesByCoPolicyNo(execution.getBusinessKey());

    policy.setCreditCheckStarted(false);

    if(execution.getCurrentActivityId().equalsIgnoreCase("update-cache-credit-check-started"))
        policy.setCreditCheckStarted(true);

    policyRepository.save(policy);

  }

}
