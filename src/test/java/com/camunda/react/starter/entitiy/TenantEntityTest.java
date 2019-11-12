package com.camunda.react.starter.entitiy;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import org.junit.*;
import org.junit.runner.*;
//import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.camunda.react.starter.LeaseRenewalTest;
import com.camunda.react.starter.entity.Lease;
import com.camunda.react.starter.entity.Tenant;
import com.camunda.react.starter.repo.LeaseRepository;
import com.camunda.react.starter.repo.TenantRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {LeaseRenewalTest.class})
public class TenantEntityTest {

    @Autowired
    private TenantRepository repository;

    @Autowired
    private LeaseRepository leaseRepository;

    @Test
    public void saveTenantTest() throws Exception {
        String property = "1180 Atlantis Ave, Lafayette Colorado CO 80026";;
       
        Lease lease = new Lease(Calendar.getInstance().getTime(), new Date(LocalDate.now().plusDays(100).getDayOfYear()), property);
        this.leaseRepository.save(lease);
        
        Tenant tenant = new Tenant("Paul Lungu", "lungu77@gmail.com", property, lease);
        this.repository.save(tenant);		
        tenant = this.repository.findByEmail("lungu77@gmail.com");
        Assert.assertTrue(tenant.getEmail().equalsIgnoreCase("lungu77@gmail.com"));
    }
}
