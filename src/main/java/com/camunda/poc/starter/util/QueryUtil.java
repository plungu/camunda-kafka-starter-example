package com.camunda.poc.starter.util;

import com.camunda.poc.starter.usecase.renewal.entity.Renewal;
import com.camunda.poc.starter.usecase.renewal.entity.Tenant;
import com.camunda.poc.starter.usecase.renewal.repo.TenantRepository;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.task.Task;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Logger;

public class QueryUtil {
	public static Logger log = Logger.getLogger(QueryUtil.class.getName());

	public static List<HistoricActivityInstance> getCompletedTasks(HistoryService historyService, String processInstanceId){

		return historyService.createHistoricActivityInstanceQuery()
		   .processInstanceId(processInstanceId)
		   .finished()
		   .orderByHistoricActivityInstanceEndTime().asc()
		   .list();
	}

	public static boolean checkTaskComplete(HistoryService historyService, String businessKey, String taskId){
		HistoricTaskInstance task =
		  historyService.createNativeHistoricTaskInstanceQuery()
		   .parameter("BusinessKey", businessKey).singleResult();
		
		if (task != null){
			log.fine("Tasks found: "+task);
			return true;
		}
		return false;
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
