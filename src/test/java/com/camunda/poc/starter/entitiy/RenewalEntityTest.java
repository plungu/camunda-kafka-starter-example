package com.camunda.poc.starter.entitiy;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.camunda.poc.starter.usecase.renewal.entity.Renewal;
import com.camunda.poc.starter.usecase.renewal.repo.RenewalRepository;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class RenewalEntityTest {

    @Autowired
    private RenewalRepository renewalRepository;
   
    @Test
    public void savePropertyTest() throws Exception {
        String property = "1180 Atlantis Ave, Lafayette Colorado CO 80026";;
    	
    	Renewal renewal = new Renewal(Calendar.getInstance().getTime(), new Date(LocalDate.now().plusDays(100).getDayOfYear()), property);
        this.renewalRepository.save(renewal);
        
    	List<Renewal> renewals = renewalRepository.findByEndDate(new Date(LocalDate.now().plusDays(100).getDayOfYear()));

    	Assert.assertNotNull(renewals);
    }
    
}
