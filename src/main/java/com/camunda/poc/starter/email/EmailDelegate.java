package com.camunda.poc.starter.email;

import com.camunda.poc.starter.bpm.LoggerDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Profile("email")
@Component("emailDelegate")
public class EmailDelegate implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

    private EmailService emailService;

    @Autowired
    public EmailDelegate(EmailService emailService){
        this.emailService = emailService;
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String emailTo = "paul.lungu@camunda.com";
        String emailSubject = "Camunda PoC Demo Email";
//         String emailbody = (String) execution.getVariable("message");
        String emailbody = "Test Message Camunda PoC Demo";

        emailService.sendSimpleMessage(emailTo, emailSubject, emailbody);
    }
}
