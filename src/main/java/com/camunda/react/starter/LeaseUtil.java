package com.camunda.react.starter;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import java.util.logging.Logger;

import com.camunda.react.starter.entity.Lease;
import com.camunda.react.starter.entity.Tenant;
import com.camunda.react.starter.repo.LeaseRepository;

public class LeaseUtil {
	public static Logger log = Logger.getLogger(LeaseUtil.class.getName());

	public static ProcessInstance startLeaseRenewal(Lease lease, 
		LeaseRepository leaseRepository, RuntimeService runtimeService, TaskService taskService, AppConfigProperties config) throws Exception{
		
		log.fine("[X] Got Lease : "+lease);
		
		String property = lease.getProperty();
		log.fine("[X] Property: "+property);
		
		List<Tenant> tenants = lease.getTennants();
		log.fine("[X] Tenants: "+tenants.size());
		
		StringBuilder recipients = new StringBuilder();
		for (int i=0; tenants.size()>i; i++){
			
			if (i==0) recipients.append(tenants.iterator().next().getEmail());
			
			else recipients.append(","+tenants.iterator().next().getEmail());	
		}
		
		List<String> emails = new ArrayList<String>();
		for (Tenant tenant : tenants){
			emails.add(tenant.getEmail());						
		}

		log.info("[X] Tennant Emails: "+emails.toString());
		 
		Date leaseExpirationDate = lease.getEnd();
		log.info("Date of Lease Expiration set to :"+leaseExpirationDate);
	    
		//Days left until final notice is sent so property has time to be listed
		int leaseExpirationBufferDays = config.getRenewalSetting().getLeaseExpirationBufferDays();
		log.info("Number of days to Lease Renewal Deadline is set to: "+leaseExpirationBufferDays);

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance();

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("leaseExpirationDate", leaseExpirationDate);
		variables.put("leaseExpirationBufferDays", leaseExpirationBufferDays);
		variables.put("finalNoticeSent", false);
		
		variables.put("tenants", emails);
		variables.put("recipients", recipients.toString());
		variables.put("gracePeriodSettings", config.getGracePeriodSettings());
		variables.put("managerEmail", config.getManagerEmail());
		variables.put("systemEmail", config.getSystemEmail());
		variables.put("defaultFromEmail", config.getDefaultFromEmail());
						
		variables.put("property", lease.getProperty());
		variables.put("endDate", dateFormatter.format(lease.getEnd()));
		variables.put("oneYearOffer", moneyFormatter.format(lease.getOneYearOffer()));
		variables.put("twoYearOffer", moneyFormatter.format(lease.getTwoYearOffer()));
        variables.put("showDate", dateFormatter.format(lease.getShowDate()));

		ProcessInstance processInstance =
		  runtimeService.startProcessInstanceByKey("lease-renewal", Long.toString(lease.getId()), variables);
		if (processInstance.getId() == null) 
			throw new Exception("Processes Id Null! Could Not Start Process for Property"+ lease.getProperty());
		
		log.info("Starterd Porocess Instance: "+ processInstance.getId());	
		
		lease.setProcessId(processInstance.getId());
		lease.setRenewalStarted(true);
		
		log.info("Tasks Service: " + taskService);
		
		String taskName = null;
		
		ActivityInstance activity = WorkflowUtil.queryActivityById(runtimeService, processInstance.getProcessInstanceId());
		if (activity != null) {
			taskName = activity.getActivityName();
		}else {
			List<Task> tasks = WorkflowUtil.queryTasksById(taskService, processInstance.getProcessInstanceId());
			if (tasks != null && !tasks.isEmpty()) {
				taskName = tasks.get(0).getName();
			}else {
				runtimeService.deleteProcessInstance(processInstance.getProcessInstanceId(), "No Tasks Found When Saving Lease");
				log.info("No tasks found for "+processInstance.getProcessInstanceId()+"! Could Not Start Process for Property: "+ lease.getProperty());				
			}			
		}
				
		lease.setWorkflowState(taskName);
		
		Lease savedLease = leaseRepository.save(lease);
		log.info("Saved Lease: "+ savedLease);	
		
		return processInstance;

	}
	
	public static Date getLeaseShowDate(Date leaseExpirationDate, int leaseExpirationBufferDays){
		LocalDate expDate = leaseExpirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate renewalDeadlineDate = expDate.minusDays(leaseExpirationBufferDays);
		return Date.from(renewalDeadlineDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
