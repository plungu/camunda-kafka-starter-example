package com.camunda.poc.starter.bpm;


import com.camunda.poc.starter.service.SGEmailService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("email")
@Component
public class SendgridEmailDelegate implements JavaDelegate {

    @Autowired
    SGEmailService sendGridEmailService;

    Logger LOG = LoggerFactory.getLogger(SendgridEmailDelegate.class);
    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;

    public void execute(DelegateExecution execution) throws Exception {

        sendGridEmailService.sendHTML("CamundaMailDemo@camunda.org",
                (String) execution.getVariable("email"),
                (String) execution.getVariable("subject"),
                (String) execution.getVariable("message"));


    }

}


