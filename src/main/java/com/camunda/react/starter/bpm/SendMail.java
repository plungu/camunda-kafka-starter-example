package com.camunda.react.starter.bpm;

import java.util.Map;

import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

/***
 * This class uses Simple Java Mail Fluent API to send mail
 * http://www.simplejavamail.org/#/about
 * @author plungu
 *
 */
public class SendMail implements JavaDelegate{
	public static Logger log = Logger.getLogger(GetRemainingDaysDelegate.class.getName());

	public void execute(DelegateExecution execution) throws Exception {
		
//		Map<String, Object> vars = execution.getVariables();
//		log.info("[ ###### VARS ##### ]: "+vars);
//		
//		String mailServerHost = (String)execution.getVariable("mailServerHost");
//		Integer mailServerPort = (Integer)execution.getVariable("mailServerPort");
//		//String mailServerDefaultFrom = (String)execution.getVariable("mailServerDefaultFrom");
//		String mailServerUserName = (String)execution.getVariable("mailServerUserName");
//		String mailServerPassword = (String)execution.getVariable("mailServerPassword");
//		Boolean mailServerUseTls = (Boolean)execution.getVariable("mailServerUseTls");
//		
//		Email email = new Email();
//		
//		email.setFromAddress("System", ((String)execution.getVariable("from")) );
//		email.addToRecipients( ((String)execution.getVariable("to")) );
//		
//		String bcc = ((String)execution.getVariable("bcc"));
//		if (bcc != null)
//			email.addBccRecipients( bcc );
//		
//		String cc = ((String)execution.getVariable("cc"));
//		if (cc != null)
//			email.addCcRecipients( cc );
//		
//		email.setSubject( ((String)execution.getVariable("subject")) );
//		
//		String text = ((String)execution.getVariable("text"));
//		if (text != null)
//			email.setText( text );
//		
//		String html = ((String)execution.getVariable("html"));
//		if (html != null)
//			email.setTextHTML( html );
//		
//		TransportStrategy strategy = TransportStrategy.SMTP_PLAIN;
//		if (mailServerUseTls) strategy = TransportStrategy.SMTP_TLS;
		
//		new Mailer("smtp.mailtrap.io", 2525, "699510e2141d4a", "4a277921fad4b5").sendMail(email);
//		new Mailer(mailServerHost, mailServerPort, mailServerUserName, mailServerPassword, strategy).sendMail(email);
		
		execution.setVariable("resultStatus", "Mail Sent!");
	}
	
}