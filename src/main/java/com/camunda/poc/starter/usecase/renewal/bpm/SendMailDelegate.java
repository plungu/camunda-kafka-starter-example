package com.camunda.poc.starter.usecase.renewal.bpm;

import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.camunda.poc.starter.usecase.renewal.AppConfigProperties;

/***
 * This class uses Simple Java Mail Fluent API to send mail
 * http://www.simplejavamail.org/#/about
 * @author plungu
 *
 */

@Component("sendMailDelegate")
public class SendMailDelegate implements JavaDelegate{
	public static Logger log = Logger.getLogger(Class.class.getName());

	AppConfigProperties config;

	@Autowired
	public SendMailDelegate(AppConfigProperties config){
		this.config = config;
	}

	public void execute(DelegateExecution execution) throws Exception {
						
		Email email = new Email();
		
		email.setFromAddress("System", ((String)execution.getVariable("from")) );
		email.addToRecipients( ((String)execution.getVariable("to")) );
		email.setText( ((String)execution.getVariable("text")) );
		email.setTextHTML( ((String)execution.getVariable("html")) );
		
		TransportStrategy strategy = TransportStrategy.SMTP_PLAIN;
		if (config.isMailServerUseTls()) strategy = TransportStrategy.SMTP_TLS;		
		
		new Mailer(config.getMailServerHost(), 
				   config.getMailServerPort(), 
				   config.getMailServerUserName(), 
				   config.getMailServerPassword(), 
				   strategy)
		.sendMail(email);
		
		execution.setVariable("resultStatus", "Mail Sent!");
	}
	
}