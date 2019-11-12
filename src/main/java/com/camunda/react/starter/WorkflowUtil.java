package com.camunda.react.starter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.task.Task;
import java.util.logging.Logger;

public class WorkflowUtil {
	public static Logger log = Logger.getLogger(WorkflowUtil.class.getName());

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
