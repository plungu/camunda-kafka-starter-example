package com.camunda.react.starter.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.camunda.react.starter.AppConfigProperties;
import com.camunda.react.starter.CamundaApplication;
import com.camunda.react.starter.LeaseUtil;
import com.camunda.react.starter.WorkflowUtil;
import com.camunda.react.starter.entity.Lease;
import com.camunda.react.starter.repo.LeaseRepository;

@Profile("java")
@RestController
public class StartRenewalController {

	public static Logger log = Logger.getLogger(CamundaApplication.class.getName());

	@Autowired LeaseRepository leaseRepository;
	@Autowired RuntimeService runtimeService;
	@Autowired TaskService taskService;
	@Autowired AppConfigProperties config;
		
    @RequestMapping(value="/renew/start", method=RequestMethod.GET)
    public ResponseEntity<HttpStatus> start(){
    	
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		
		log.fine("[X] Running Lease renewal");
		//kicks off worklfow when the end date is 100 from current date
		Date leaseRenewalkickoffDate = WorkflowUtil.getKickOffDate(config.getCron().getRenewalKickoffBufferDays());
		log.fine("[X] Start date from today: "+leaseRenewalkickoffDate);

		List<Lease> leases = leaseRepository.findByEndDate(leaseRenewalkickoffDate);
		if (!leases.isEmpty()){
			for(Lease lease : leases){
				try {
					LeaseUtil.startLeaseRenewal(lease, leaseRepository, runtimeService, taskService, config);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			log.fine("[X] No leases found ending with kick off date: "+leaseRenewalkickoffDate);
		}
    	
		return re;
    }
}
