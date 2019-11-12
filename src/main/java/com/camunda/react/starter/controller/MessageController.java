package com.camunda.react.starter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camunda.react.starter.WorkflowUtil;
import com.camunda.react.starter.entity.Lease;
import com.camunda.react.starter.entity.Message;
import com.camunda.react.starter.entity.Tenant;
import com.camunda.react.starter.repo.LeaseRepository;
import com.camunda.react.starter.repo.MessageRepository;
import com.camunda.react.starter.repo.TenantRepository;

@RestController
public class MessageController {
	public static Logger log = Logger.getLogger(MessageController.class.getName());

    @Autowired RuntimeService runtimeService;

    @Autowired TaskService taskService;
    
    @Autowired HistoryService historyService;
    
    @Autowired MessageRepository messageRepository;
    
    @Autowired LeaseRepository leaseRepository;

    @Autowired TenantRepository tenantRepository;

    /**
     * Controller Request Mapping that processes request from the ReactUI 
     * Updates the renewal state
     * 
     * @param confirm
     * @param renewing
     * @param signed
     * @param text
     * @param subject
     * @param gracePeriod
     * @param processId
     * @param leaseId
     * @return
     */
    @RequestMapping(value="/message", method= RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<HttpStatus> sendMessage(     								   
    		   @RequestParam(value = "confirm", required = false) Boolean confirm,
    		   @RequestParam(value = "renewing", required = false) Boolean renewing,
    		   @RequestParam(value = "signed", required = false) Boolean signed,
			   @RequestParam(value = "text") String text,
			   @RequestParam(value = "subject") String subject,
			   @RequestParam(value = "gracePeriod", required = false) Integer gracePeriod,
			   @RequestParam(value = "processId") String processId,
			   @RequestParam(value = "leaseId", required = false) String leaseId)			   
    {
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		List<Task> tasks = WorkflowUtil.queryTasksById(taskService, processId);
    	if (tasks.isEmpty()){
    		//TODO: send message to property manager tenant sent a message but has no tasks.
	    	log.fine("[X] No tasks found for :"+processId);
    	}
    	
		Task task = tasks.get(0);
		log.fine("Completing Task: "+task.getName());		
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("subject", subject);
		variables.put("message", text);
		variables.put("renewalConfirmed", confirm);
		if(gracePeriod != null){
			if (gracePeriod == 5)
				variables.put("gracePeriod", "0 0 0/5 * * ?");
			else if (gracePeriod == 3)
				variables.put("gracePeriod", "0 0 0/3 * * ?");
			else if (gracePeriod == 2)
				variables.put("gracePeriod", "0 0 0/2 * * ?");		
			else if (gracePeriod == 1)
				variables.put("gracePeriod", "0 0 0/1 * * ?");
		}
		
		taskService.complete(task.getId(), variables);
    	
		return re;
    }
    	
    /**
     * parses the email message from transactional mail provider
     * @param spf
     * @param attachments
     * @param dkim
     * @param sender_ip
     * @param headers
     * @param subject
     * @param html
     * @param envelope
     * @param text
     * @param charsets
     * @param from
     * @param to
     * @return
     */
    @RequestMapping(value="/parse", method= RequestMethod.POST, consumes = {"multipart/form-data; boundary=xYzZY"})
    public ResponseEntity<HttpStatus> completeTask(@RequestParam("SPF") String spf, 
    								   @RequestParam(value = "attachments", required = false) String attachments,
    								   @RequestParam(value = "dkim", required = false) String dkim,
    								   @RequestParam(value = "sender_ip", required = false) String sender_ip,
    								   @RequestParam(value = "headers", required = false) String headers,
    								   @RequestParam(value = "subject", required = false) String subject,
    								   @RequestParam(value = "html", required = false) String html,
    								   @RequestParam(value = "envelope", required = false) String envelope,
    								   @RequestParam(value = "text", required = false) String text,
    								   @RequestParam(value = "charsets", required = false) String charsets,
    								   @RequestParam("from") String from,
    								   @RequestParam("to") String to
    		) {
        
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);
    	
	    log.info("[X] Parsing Email ");
    		    
		try {
			if (spf == null){
				re = new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
	    		throw new Exception("[X] No multipart form data found in request!");
			}else{ 
				log.info("[X] MultiPart Data Recieved: "+spf);
															
				String fromEmail = new InternetAddress(from).getAddress();
				String toEmail = new InternetAddress(to).getAddress();
				
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
				
				//save the message from the request
				Message message = new Message();
				message.setSender(fromEmail);
				message.setRecipient(toEmail);
				message.setSubject(subject);
				message.setText(text);
				message.setHtml(html);
				message.setLease(lease);
				messageRepository.save(message);
				log.info("[X] Saved email from : "+new InternetAddress(from).getAddress());
				log.info("\n[X] About : "+subject);					
				
				runtimeService.correlateMessage("tenant_reply_message", Long.toString(lease.getId()));
				log.info("[X] Corrlating Message tenant_reply_message for :" + lease.getProperty() );

			}
			
		} catch (Exception e){
			e.printStackTrace();
		}

		return re;
    }

}
