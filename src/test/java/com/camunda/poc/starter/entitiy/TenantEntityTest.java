package com.camunda.poc.starter.entitiy;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import com.camunda.poc.starter.usecase.renewal.entity.Renewal;
import com.camunda.poc.starter.usecase.renewal.entity.Tenant;
import com.camunda.poc.starter.usecase.renewal.repo.RenewalRepository;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.camunda.poc.starter.usecase.renewal.repo.TenantRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class TenantEntityTest {

    @Autowired
    private TenantRepository repository;

    @Autowired
    private RenewalRepository renewalRepository;

    @Test
    public void saveTenantTest() throws Exception {
        String property = "1180 Atlantis Ave, Lafayette Colorado CO 80026";;
       
        Renewal renewal = new Renewal(Calendar.getInstance().getTime(), new Date(LocalDate.now().plusDays(100).getDayOfYear()), property);
        this.renewalRepository.save(renewal);
        
        Tenant tenant = new Tenant("Paul Lungu", "lungu77@gmail.com", property, renewal);
        this.repository.save(tenant);		
        tenant = this.repository.findByEmail("lungu77@gmail.com");
        Assert.assertTrue(tenant.getEmail().equalsIgnoreCase("lungu77@gmail.com"));
    }
}
