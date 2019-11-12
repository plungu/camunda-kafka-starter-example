package com.camunda.react.starter.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.camunda.react.starter.repo.TenantRepository;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.camunda.react.starter.AppConfigProperties;
import com.camunda.react.starter.LeaseUtil;
import com.camunda.react.starter.csv.Lease;
import com.camunda.react.starter.entity.CannedMessage;
import com.camunda.react.starter.entity.Tenant;
import com.camunda.react.starter.repo.CannedMessageRepository;
import com.camunda.react.starter.repo.LeaseRepository;

@RestController
public class ImportController {
	public static Logger log = Logger.getLogger(MessageController.class.getName());

	@Autowired LeaseRepository leaseRepository;

    @Autowired TenantRepository tenantRepository;
    
    @Autowired CannedMessageRepository cannedMessageRepository;
    
    @Autowired AppConfigProperties config;

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
					Date showDate = LeaseUtil.getLeaseShowDate(bean.getEnd(), leaseExpirationBufferDays);
					log.fine("Lease Renewal Deadline (show date) is set to: "+showDate);
					
					com.camunda.react.starter.entity.Lease lease = new com.camunda.react.starter.entity.Lease(bean.getStart(), bean.getEnd(), bean.getPropertySlug());
					lease.setCurrentRent(bean.getCurrentRent());
					lease.setOneYearOffer(bean.getOneYearOffer());
					lease.setTwoYearOffer(bean.getTwoYearOffer());
					lease.setShowDate(showDate);
					lease.setRenewalCompleted(false);
					lease.setRenewalStarted(false);
					List<Tenant> tenants = tenantRepository.findTenantsByUnitSlug(lease.getProperty());
					lease.setTenants(tenants);
					com.camunda.react.starter.entity.Lease newlease = leaseRepository.save(lease);
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
        List<com.camunda.react.starter.csv.Tenant> beanList = null; 
		try {
			reader = new CSVReader(new InputStreamReader(file.getInputStream()));
	        HeaderColumnNameMappingStrategy<com.camunda.react.starter.csv.Tenant> strategy = new HeaderColumnNameMappingStrategy<com.camunda.react.starter.csv.Tenant>();
	        strategy.setType(com.camunda.react.starter.csv.Tenant.class);
	        CsvToBean<com.camunda.react.starter.csv.Tenant> csvToBean = new CsvToBean<com.camunda.react.starter.csv.Tenant>();
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
				for (com.camunda.react.starter.csv.Tenant bean : beanList){
					//TODO: support multiple emails
					com.camunda.react.starter.entity.Tenant tenant = new com.camunda.react.starter.entity.Tenant(bean.getName(), bean.getEmail(), bean.getPropertySlug());
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
