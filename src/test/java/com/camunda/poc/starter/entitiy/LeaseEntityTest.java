package com.camunda.poc.starter.entitiy;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.camunda.poc.starter.usecase.renewal.entity.Lease;
import com.camunda.poc.starter.usecase.renewal.repo.LeaseRepository;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class LeaseEntityTest {

    @Autowired
    private LeaseRepository leaseRepository;
   
    @Test
    public void savePropertyTest() throws Exception {
        String property = "1180 Atlantis Ave, Lafayette Colorado CO 80026";;
    	
    	Lease lease = new Lease(Calendar.getInstance().getTime(), new Date(LocalDate.now().plusDays(100).getDayOfYear()), property);
        this.leaseRepository.save(lease);
        
    	List<Lease> leases = leaseRepository.findByEndDate(new Date(LocalDate.now().plusDays(100).getDayOfYear()));

    	Assert.assertNotNull(leases);
    }
    
}
