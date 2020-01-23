package com.camunda.poc.starter.usecase.renewal.controller;

import com.camunda.poc.starter.controller.ParseSendGridMessageController;
import com.camunda.poc.starter.usecase.renewal.AppConfigProperties;
import com.camunda.poc.starter.usecase.renewal.RenewalUtil;
import com.camunda.poc.starter.usecase.renewal.csv.Lease;
import com.camunda.poc.starter.usecase.renewal.csv.Tenant;
import com.camunda.poc.starter.usecase.renewal.entity.CannedMessage;
import com.camunda.poc.starter.usecase.renewal.repo.CannedMessageRepository;
import com.camunda.poc.starter.usecase.renewal.repo.LeaseRepository;
import com.camunda.poc.starter.usecase.renewal.repo.TenantRepository;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class RenewalImportController {
	public static Logger log = Logger.getLogger(ParseSendGridMessageController.class.getName());

	LeaseRepository leaseRepository;

    TenantRepository tenantRepository;

    CannedMessageRepository cannedMessageRepository;

    AppConfigProperties config;

	@Autowired
	public RenewalImportController(LeaseRepository leaseRepository,
								   TenantRepository tenantRepository,
								   CannedMessageRepository cannedMessageRepository,
								   AppConfigProperties config) {
		this.leaseRepository = leaseRepository;
		this.tenantRepository = tenantRepository;
		this.cannedMessageRepository = cannedMessageRepository;
		this.config = config;
	}

    @RequestMapping(value="/renewals/start", method= RequestMethod.GET)
    public ResponseEntity<HttpStatus> autoImport()
    {
    	
        CSVReader reader = null;
    	ResponseEntity<HttpStatus> tenantStatus = importTenants(reader);
    	if(tenantStatus.getStatusCode().equals(HttpStatus.OK)) {
    		return importLeases(reader);
    	} else {
    		return tenantStatus;
    	}
    }    
    
    private ResponseEntity<HttpStatus> importTenants(CSVReader reader) {
        List<Tenant>beanListTenant = null;
		try {
			reader = new CSVReader(new InputStreamReader(new FileInputStream(new File("test-data/tenants.csv"))));
	        HeaderColumnNameMappingStrategy<Tenant> strategy = new HeaderColumnNameMappingStrategy<Tenant>();
	        strategy.setType(Tenant.class);
	        CsvToBean<Tenant> csvToBean = new CsvToBean<Tenant>();
	        beanListTenant = csvToBean.parse(strategy, reader);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (beanListTenant == null){
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		
		}else{
			try {
				for (Tenant bean : beanListTenant){
					//TODO: support multiple emails
					com.camunda.poc.starter.usecase.renewal.entity.Tenant tenant = new com.camunda.poc.starter.usecase.renewal.entity.Tenant(bean.getName(), bean.getEmail(), bean.getPropertySlug());
					tenantRepository.save(tenant);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}	
		}
		
    	return new ResponseEntity<HttpStatus>(HttpStatus.OK);

    }
    
    
    private ResponseEntity<HttpStatus> importLeases(CSVReader reader) {
        List<Lease> beanListLease = null;
		try {
			reader = new CSVReader(new InputStreamReader(new FileInputStream(new File("test-data/leases.csv"))));
	        HeaderColumnNameMappingStrategy<Lease> strategy = new HeaderColumnNameMappingStrategy<Lease>();
	        strategy.setType(Lease.class);
	        CsvToBean<Lease> csvToBean = new CsvToBean<Lease>();
	        beanListLease = csvToBean.parse(strategy, reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (beanListLease == null){
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		}else{
			try {
				for (Lease bean : beanListLease){				    
					//Days left until final notice is sent so property has time to be listed
					int leaseExpirationBufferDays = config.getRenewalSetting().getLeaseExpirationBufferDays();
					log.fine("Number of days to Lease Renewal Deadline is set to: "+leaseExpirationBufferDays);
					Date showDate = RenewalUtil.getLeaseShowDate(bean.getEnd(), leaseExpirationBufferDays);
					log.fine("Lease Renewal Deadline (show date) is set to: "+showDate);
					
					com.camunda.poc.starter.usecase.renewal.entity.Lease lease = new com.camunda.poc.starter.usecase.renewal.entity.Lease(bean.getStart(), bean.getEnd(), bean.getPropertySlug());
					lease.setCurrentRent(bean.getCurrentRent());
					lease.setOneYearOffer(bean.getOneYearOffer());
					lease.setTwoYearOffer(bean.getTwoYearOffer());
					lease.setShowDate(showDate);
					lease.setRenewalCompleted(false);
					lease.setRenewalStarted(false);
					List<com.camunda.poc.starter.usecase.renewal.entity.Tenant> tenants = tenantRepository.findTenantsByUnitSlug(lease.getProperty());
					lease.setTenants(tenants);
					com.camunda.poc.starter.usecase.renewal.entity.Lease newlease = leaseRepository.save(lease);
					tenants.forEach(tenant -> {
						tenant.setLease(newlease);
						tenantRepository.save(tenant);
					});
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}	
		}
		
    	return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
    
    /**
     * 
     * @param file
     * @return
     */
    @RequestMapping(value="/importlease", method= RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<HttpStatus> importLease(
    		 @RequestParam(value = "file") MultipartFile file)
    {
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);
    	
        CSVReader reader = null;
        List<Lease> beanList = null; 
		try {
			reader = new CSVReader(new InputStreamReader(file.getInputStream()));
	        HeaderColumnNameMappingStrategy<Lease> strategy = new HeaderColumnNameMappingStrategy<Lease>();
	        strategy.setType(Lease.class);
	        CsvToBean<Lease> csvToBean = new CsvToBean<Lease>();
	        beanList = csvToBean.parse(strategy, reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			re = new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (beanList == null){
			re = new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		}else{
			try {
				for (Lease bean : beanList){				    
					//Days left until final notice is sent so property has time to be listed
					int leaseExpirationBufferDays = config.getRenewalSetting().getLeaseExpirationBufferDays();
					log.fine("Number of days to Lease Renewal Deadline is set to: "+leaseExpirationBufferDays);
					Date showDate = RenewalUtil.getLeaseShowDate(bean.getEnd(), leaseExpirationBufferDays);
					log.fine("Lease Renewal Deadline (show date) is set to: "+showDate);
					
					com.camunda.poc.starter.usecase.renewal.entity.Lease lease = new com.camunda.poc.starter.usecase.renewal.entity.Lease(bean.getStart(), bean.getEnd(), bean.getPropertySlug());
					lease.setCurrentRent(bean.getCurrentRent());
					lease.setOneYearOffer(bean.getOneYearOffer());
					lease.setTwoYearOffer(bean.getTwoYearOffer());
					lease.setShowDate(showDate);
					lease.setRenewalCompleted(false);
					lease.setRenewalStarted(false);
					List<com.camunda.poc.starter.usecase.renewal.entity.Tenant> tenants = tenantRepository.findTenantsByUnitSlug(lease.getProperty());
					lease.setTenants(tenants);
					com.camunda.poc.starter.usecase.renewal.entity.Lease newlease = leaseRepository.save(lease);
					tenants.forEach(tenant -> {
						tenant.setLease(newlease);
						tenantRepository.save(tenant);
					});
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				re = new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}	
		}
        
		return re;
    }

    /**
     * 
     * @param file
     * @return
     */
    @RequestMapping(value="/importtenant", method= RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<HttpStatus> importTenant(
    		 @RequestParam(value = "file") MultipartFile file)
    {
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);
    	
        CSVReader reader = null;
        List<Tenant> beanList = null;
		try {
			reader = new CSVReader(new InputStreamReader(file.getInputStream()));
	        HeaderColumnNameMappingStrategy<Tenant> strategy = new HeaderColumnNameMappingStrategy<Tenant>();
	        strategy.setType(Tenant.class);
	        CsvToBean<Tenant> csvToBean = new CsvToBean<Tenant>();
	        beanList = csvToBean.parse(strategy, reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			re = new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (beanList == null){
			re = new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		}else{
			try {
				for (Tenant bean : beanList){
					//TODO: support multiple emails
					com.camunda.poc.starter.usecase.renewal.entity.Tenant tenant = new com.camunda.poc.starter.usecase.renewal.entity.Tenant(bean.getName(), bean.getEmail(), bean.getPropertySlug());
					tenantRepository.save(tenant);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				re = new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}	
		}
        
		return re;
    }

    /**
     * 
     * @param file
     * @return
     */
    @RequestMapping(value="/importcannedmessage", method= RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<HttpStatus> importCannedMessage(
    		 @RequestParam(value = "file") MultipartFile file)
    {
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);
    	
        CSVReader reader = null;
        List<CannedMessage> beanList = null; 
		try {
			reader = new CSVReader(new InputStreamReader(file.getInputStream()));
	        HeaderColumnNameMappingStrategy<CannedMessage> strategy = new HeaderColumnNameMappingStrategy<CannedMessage>();
	        strategy.setType(CannedMessage.class);
	        CsvToBean<CannedMessage> csvToBean = new CsvToBean<CannedMessage>();
	        beanList = csvToBean.parse(strategy, reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			re = new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (beanList == null){
			re = new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
		}else{
			try {
				for (CannedMessage bean : beanList){
					//TODO: support multiple emails
					
					cannedMessageRepository.save(bean);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				re = new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
			}	
		}
        
		return re;
    }
}
