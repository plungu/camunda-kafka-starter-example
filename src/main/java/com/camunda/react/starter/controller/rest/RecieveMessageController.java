package com.camunda.react.starter.controller.rest;

import javax.mail.internet.InternetAddress;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.camunda.react.starter.WorkflowUtil;
import com.camunda.react.starter.entity.Lease;
import com.camunda.react.starter.entity.Message;
import com.camunda.react.starter.repo.LeaseRepository;
import com.camunda.react.starter.repo.MessageRepository;
import com.camunda.react.starter.repo.TenantRepository;

@Profile("rest")
@RestController
public class RecieveMessageController {
	public static Logger log = Logger.getLogger(RecieveMessageController.class.getName());

    @Autowired RuntimeService runtimeService;

    @Autowired TaskService taskService;
    
    @Autowired HistoryService historyService;
    
    @Autowired MessageRepository messageRepository;
    
    @Autowired LeaseRepository leaseRepository;

    @Autowired TenantRepository tenantRepository;

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
				
				Lease lease = WorkflowUtil.getLeaseByTenantEamil(tenantRepository, fromEmail, toEmail);
				
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
				
				//TODO: use REST to correlate message recive
				runtimeService.correlateMessage("tenant_reply_message", Long.toString(lease.getId()));
				log.info("[X] Corrlating Message tenant_reply_message for :" + lease.getProperty() );

			}
			
		} catch (Exception e){
			e.printStackTrace();
		}

		return re;
    }

}
