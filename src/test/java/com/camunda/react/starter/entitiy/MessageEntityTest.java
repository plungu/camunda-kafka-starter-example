package com.camunda.react.starter.entitiy;

import java.util.Calendar;
import java.util.List;

import org.junit.*;
import org.junit.runner.*;
//import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.camunda.react.starter.LeaseRenewalTest;
import com.camunda.react.starter.entity.Lease;
import com.camunda.react.starter.entity.Message;
import com.camunda.react.starter.repo.LeaseRepository;
import com.camunda.react.starter.repo.MessageRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {LeaseRenewalTest.class})
public class MessageEntityTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private LeaseRepository leaseRepository;

    @Test
    public void saveMessageTest() throws Exception {
        Lease lease = new Lease(Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), null);
        this.leaseRepository.save(lease);
    	
        Message message = new Message("lungu77@gmail.com", "admin@parse.test.com", "Time to renew your lease", "Yes I would like to renew my lease", null, lease);
        this.messageRepository.save(message);
        
        List<Message> messages = this.messageRepository.findAllByLease(lease);
        Assert.assertTrue(messages.get(0).getText().equalsIgnoreCase("Yes I would like to renew my lease"));
    }
}
