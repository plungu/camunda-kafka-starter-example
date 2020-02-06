package com.camunda.poc.starter.entitiy;

import java.util.Calendar;
import java.util.List;

import com.camunda.poc.starter.usecase.renewal.entity.Renewal;
import com.camunda.poc.starter.usecase.renewal.entity.Message;
import com.camunda.poc.starter.usecase.renewal.repo.RenewalRepository;
import com.camunda.poc.starter.usecase.renewal.repo.MessageRepository;
import org.junit.*;
import org.junit.runner.*;
//import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class MessageEntityTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RenewalRepository leaseRepository;

    @Test
    public void saveMessageTest() throws Exception {
        Renewal lease = new Renewal(Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), null);
        this.leaseRepository.save(lease);
    	
        Message message = new Message("lungu77@gmail.com", "admin@parse.test.com", "Time to renew your lease", "Yes I would like to renew my lease", null, lease);
        this.messageRepository.save(message);
        
        List<Message> messages = this.messageRepository.findAllByRenewal(lease);
        Assert.assertTrue(messages.get(0).getText().equalsIgnoreCase("Yes I would like to renew my lease"));
    }
}
