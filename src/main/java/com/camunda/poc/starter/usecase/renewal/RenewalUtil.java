package com.camunda.poc.starter.usecase.renewal;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import com.camunda.poc.starter.usecase.renewal.repo.LeaseRepository;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import com.camunda.poc.starter.usecase.renewal.entity.Lease;
import com.camunda.poc.starter.usecase.renewal.entity.Tenant;
import com.camunda.poc.starter.usecase.renewal.repo.TenantRepository;

import java.util.logging.Logger;

public class RenewalUtil {
	public static Logger log = Logger.getLogger(RenewalUtil.class.getName());

	public static ProcessInstance startLeaseRenewal(LeaseRepository leaseRepository,
													RuntimeService runtimeService,
													TaskService taskService,
													AppConfigProperties config){

		ProcessInstance processInstance = null;
		log.fine("[X] Running Lease renewal");
		//kicks off worklfow when the end date is 100 from current date
		Date leaseRenewalkickoffDate = RenewalUtil.getKickOffDate(config.getCron().getRenewalKickoffBufferDays());
		log.fine("[X] Start date from today: "+leaseRenewalkickoffDate);

		List<Lease> leases = leaseRepository.findByEndDate(leaseRenewalkickoffDate);
		if (!leases.isEmpty()){
			for(Lease lease : leases){
				try {
					processInstance = RenewalUtil.startLeaseRenewal(lease, leaseRepository, runtimeService, taskService, config);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			log.fine("[X] No leases found ending with kick off date: "+leaseRenewalkickoffDate);
		}
		return processInstance;
	}

	public static ProcessInstance startLeaseRenewal(Lease lease,
													LeaseRepository leaseRepository,
													RuntimeService runtimeService,
													TaskService taskService,
													AppConfigProperties config) throws Exception{

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


		String businessKey = UUID.randomUUID().toString();
		ProcessInstance processInstance =
				runtimeService.startProcessInstanceByKey("renewal-process-example", businessKey, variables);
		if (processInstance.getId() == null)
			throw new Exception("Processes Id Null! Could Not Start Process for Property"+ lease.getProperty());

		log.info("Starterd Porocess Instance: "+ processInstance.getId());

		lease.setBusinessKey(businessKey);
		lease.setRenewalStarted(true);

		log.info("Tasks Service: " + taskService);

		String taskName = null;

		ActivityInstance activity = RenewalUtil.queryActivityById(runtimeService, processInstance.getProcessInstanceId());
		if (activity != null) {
			taskName = activity.getActivityName();
		}else {
			List<Task> tasks = RenewalUtil.queryTasksById(taskService, processInstance.getProcessInstanceId());
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

	public static Lease getLeaseByTenantEamil(TenantRepository tenantRepository, String fromEmail, String toEmail) throws Exception {
		//check if sender os the recipiant is a tenant before saving
		Tenant tenant = tenantRepository.findByEmail(fromEmail);
		if (tenant == null)
			tenant = tenantRepository.findByEmail(toEmail);
		
		if (tenant == null)
			throw new Exception("No Tenant found for message");
		
		log.info("[X] Found tenant : "+tenant.getEmail());
		
		Lease lease = tenant.getLease();
		if (lease == null)
			throw new Exception("No lease found for message");
		
		return lease;

	}
	
	public static void printCompletedTasks(HistoryService historyService, String processInstanceId){
		List<HistoricActivityInstance> activities =
		  historyService.createHistoricActivityInstanceQuery()
		   .processInstanceId(processInstanceId)
		   .finished()
		   .orderByHistoricActivityInstanceEndTime().asc()
		   .list();

		for (HistoricActivityInstance activity : activities) {
		  System.out.println(activity.getActivityId() + " took "
		    + activity.getDurationInMillis() + " milliseconds");
		}

	}

	public static boolean checkTaskComplete(HistoryService historyService, String processInstanceId, String taskId){
		List<HistoricActivityInstance> activities =
		  historyService.createHistoricActivityInstanceQuery()
		   .processInstanceId(processInstanceId)
		   .finished()
		   .list();
		
		log.fine("Tasks found: "+activities.size());

		for (HistoricActivityInstance activity : activities) {
			log.fine("Task Id: "+activity.getActivityId());
			boolean result = (activity.getActivityId().equalsIgnoreCase(taskId)) ? true : false;
			if (result){
				return true;
			}
		}
		log.fine("Did not find task: "+taskId);
		return false;
	}

	/**
	 * Checks the task history for task
	 */
	public static void printActiveTasks(HistoryService historyService, String processInstanceId){
		
		List<HistoricActivityInstance> activities =
		  historyService.createHistoricActivityInstanceQuery()
		   .processInstanceId(processInstanceId)
		   .unfinished()
		   .list();
	
		for (HistoricActivityInstance activity : activities) {
			System.out.println("Running Task: "+activity.getActivityId() + " active for "
					    + activity.getDurationInMillis() + " milliseconds");
		}
	}

	public static boolean checkTaskActive(HistoryService historyService, String processInstanceId, String taskId){
		List<HistoricActivityInstance> activities =
		  historyService.createHistoricActivityInstanceQuery()
		   .processInstanceId(processInstanceId)
		   .unfinished()
		   .list();
		
		log.fine("Tasks found: "+activities.size());

		for (HistoricActivityInstance activity : activities) {
			log.fine("Task Id: "+activity.getActivityId());
			boolean result = (activity.getActivityId().equalsIgnoreCase(taskId)) ? true : false;
			if (result){
				return true;
			}
		}
		log.fine("Did not find task: "+taskId);
		return false;
	}

	public static int checkTaskCount(HistoryService historyService, String processInstanceId, String taskId){
		/*
		 * Get the History Service
		 * print out the workflow history
		 */		
		List<HistoricActivityInstance> activities =
		  historyService.createHistoricActivityInstanceQuery()
		   .processInstanceId(processInstanceId)
		   .finished()
		   .orderByHistoricActivityInstanceEndTime().asc()
		   .list();
		int counter = 0;
		for (HistoricActivityInstance activity : activities) {
			boolean result = (activity.getActivityId().equalsIgnoreCase(taskId)) ? true : false;
			if (result){
				counter++;
			}
		}
		if (counter == 0) log.fine("Did not find task: "+taskId);
		return counter;
	}
	
	public static List<Task> queryTasksById(final TaskService taskService, String id){
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(id).list();
		return tasks;
	}

	public static ActivityInstance queryActivityById(final RuntimeService runtimeService, String processInstanceId){
		ActivityInstance activity = runtimeService.getActivityInstance(processInstanceId);
		return activity;
	}

	public static List<Task> queryTasksByGroup(final TaskService taskService, String group){
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(group).list();
		return tasks;
	}
	
	public static List<Task> queryTasksByCandidate(final TaskService taskService, String user){
		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(user).list();
		return tasks;
	}
	
	public static Date getKickOffDate(int days){
		return Date.from(LocalDate.now().plusDays(days).atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
