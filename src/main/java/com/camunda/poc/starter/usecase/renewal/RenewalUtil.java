package com.camunda.poc.starter.usecase.renewal;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import com.camunda.poc.starter.usecase.renewal.entity.CamundaRenewalTask;
import com.camunda.poc.starter.usecase.renewal.repo.CamundaRenewalTaskRepository;
import com.camunda.poc.starter.usecase.renewal.repo.RenewalRepository;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

import com.camunda.poc.starter.usecase.renewal.entity.Renewal;
import com.camunda.poc.starter.usecase.renewal.entity.Tenant;
import com.camunda.poc.starter.usecase.renewal.repo.TenantRepository;

import java.util.logging.Logger;

public class RenewalUtil {
	public static Logger log = Logger.getLogger(RenewalUtil.class.getName());

	public static ProcessInstance startRenewal(RenewalRepository renewalRepository,
											   CamundaRenewalTaskRepository camundaRenewalTaskRepository,
											   RuntimeService runtimeService,
											   AppConfigProperties config){

		ProcessInstance processInstance = null;
		log.info("\n\n Running Renewal");
		//kicks off worklfow when the end date is 100 from current date
		Date renewalkickoffDate = RenewalUtil
				.getKickOffDate(config.getCron()
						.getRenewalKickoffBufferDays());

		log.info("\n\n Start date from today: "+renewalkickoffDate);

		List<Renewal> renewals = renewalRepository
				.findByEndDate(renewalkickoffDate);

		if (!renewals.isEmpty()){
			for(Renewal renewal : renewals){
				try {
					processInstance = RenewalUtil
							.startRenewal(renewal,
									renewalRepository,
									camundaRenewalTaskRepository,
									runtimeService,
									config);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			log.info("\n\n No leases found ending with kick off date: "+renewalkickoffDate);
		}
		return processInstance;
	}

	public static ProcessInstance startRenewal(Renewal renewal,
											   RenewalRepository renewalRepository,
											   CamundaRenewalTaskRepository camundaRenewalTaskRepository,
											   RuntimeService runtimeService,
											   AppConfigProperties config) throws Exception{

		log.info("\n\n Got Renewal : "+renewal);

		String property = renewal.getProperty();
		log.fine("\n\n Property: "+property);

		List<Tenant> tenants = renewal.getTennants();
		log.fine("\n\n Tenants: "+tenants.size());

		StringBuilder recipients = new StringBuilder();
		for (int i=0; tenants.size()>i; i++){

			if (i==0) recipients.append(tenants.iterator().next().getEmail());

			else recipients.append(","+tenants.iterator().next().getEmail());
		}

		List<String> emails = new ArrayList<String>();
		for (Tenant tenant : tenants){
			emails.add(tenant.getEmail());
		}

		log.info("\n\n Tennant Emails: "+emails.toString());

		Date leaseExpirationDate = renewal.getEnd();
		log.info("\n\n Date of Renewal Expiration set to :"+leaseExpirationDate);

		//Days left until final notice is sent so property has time to be listed
		int leaseExpirationBufferDays = config.getRenewalSetting().getRenewalExpirationBufferDays();
		log.info("\n\n Number of days to Renewal Renewal Deadline is set to: "+leaseExpirationBufferDays);

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

		variables.put("property", renewal.getProperty());
		variables.put("endDate", dateFormatter.format(renewal.getEnd()));
		variables.put("oneYearOffer", moneyFormatter.format(renewal.getOneYearOffer()));
		variables.put("twoYearOffer", moneyFormatter.format(renewal.getTwoYearOffer()));
		variables.put("showDate", dateFormatter.format(renewal.getShowDate()));

		//TODO: ALL this should be in a transaction

		//Save the lease with a biz key
		String businessKey = UUID.randomUUID().toString();

		renewal.setBusinessKey(businessKey);
		renewal.setRenewalStarted(true);
		Renewal savedRenewal = renewalRepository.save(renewal);
		log.info("Saved Renewal: "+ savedRenewal);

		//start a process with the biz key and renewal
		ProcessInstance processInstance = null;
		if(savedRenewal != null) {
			processInstance =
					runtimeService
							.startProcessInstanceByKey("renewal-process-example",
									businessKey,
									variables);

			if (processInstance.getId() == null)
				throw new Exception("\n\n Processes Id Null! Could Not Start Process for Property" + renewal.getProperty());

			String taskId = runtimeService.getActivityInstance(processInstance.getProcessInstanceId()).getActivityId();
			camundaRenewalTaskRepository.save(new CamundaRenewalTask(savedRenewal.getId(), taskId));

		}else{
			throw new Exception("\n\n Could not save Renewal");
		}


		log.info("\n\n Started Process Instance: "+ processInstance.getId());

		return processInstance;

	}

	public static Date getRenewalShowDate(Date leaseExpirationDate, int leaseExpirationBufferDays){
		LocalDate expDate = leaseExpirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate renewalDeadlineDate = expDate.minusDays(leaseExpirationBufferDays);
		return Date.from(renewalDeadlineDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Renewal getRenewalByTenantEamil(TenantRepository tenantRepository, String fromEmail, String toEmail) throws Exception {
		//check if sender os the recipiant is a tenant before saving
		Tenant tenant = tenantRepository.findByEmail(fromEmail);
		if (tenant == null)
			tenant = tenantRepository.findByEmail(toEmail);
		
		if (tenant == null)
			throw new Exception("No Tenant found for message");
		
		log.info("[X] Found tenant : "+tenant.getEmail());
		
		Renewal lease = tenant.getRenewal();
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
	
	public static List<Task> queryTasksByBizKey(final TaskService taskService, String id){
		List<Task> tasks = taskService.createTaskQuery().processInstanceBusinessKey(id).list();
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
