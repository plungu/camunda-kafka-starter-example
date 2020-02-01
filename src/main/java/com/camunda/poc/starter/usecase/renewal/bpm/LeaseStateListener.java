package com.camunda.poc.starter.usecase.renewal.bpm;

import com.camunda.poc.starter.usecase.renewal.RenewalUtil;
import com.camunda.poc.starter.usecase.renewal.entity.Lease;
import com.camunda.poc.starter.usecase.renewal.repo.LeaseRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component("leaseStateListener")
public class LeaseStateListener implements ExecutionListener {

    public static Logger log = Logger.getLogger(Class.class.getName());

    LeaseRepository leaseRepository;

    @Autowired
    public LeaseStateListener(LeaseRepository leaseRepository){
        this.leaseRepository = leaseRepository;
    }

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String taskName = execution.getCurrentActivityName();
        Lease lease = leaseRepository.findLeaseByBusinessKey(execution.getProcessBusinessKey());
        lease.setWorkflowState(taskName);
        leaseRepository.save(lease);
        log.info("Updating Lease State with Task: "+taskName);
    }
}
