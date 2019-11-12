package com.camunda.react.starter.controller;

import java.util.Optional;

//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.rest.webmvc.RepositoryRestController;
//import org.springframework.data.web.PagedResourcesAssembler;
//import org.springframework.hateoas.ExposesResourceFor;
//import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.camunda.react.starter.WorkflowUtil;
import com.camunda.react.starter.entity.Lease;
import com.camunda.react.starter.repo.LeaseRepository;
//import com.camunda.react.starter.resource.LeaseResource;
//import com.camunda.react.starter.resource.LeaseResourceAssembler;

//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

//TODO: Take a deeper look at resource assembler and overriding defaults in Repo and Controller

@RestController
//@RepositoryRestController
//@ExposesResourceFor(LeaseResource.class)
public class LeaseController {

	@Autowired LeaseRepository leaseRepository;
	
//	@Autowired PagedResourcesAssembler<?> resourceAssembler;
	
//	@RequestMapping(method=RequestMethod.GET, value = "/leases")
//	public ResponseEntity<List<LeaseResource>> leases() {
//		List<Lease> leases = leaseRepository.findByPriority();
//		LeaseResourceAssembler assembler = new LeaseResourceAssembler();
//		List<LeaseResource> leaseResources = assembler.toResources(leases);
//		return new ResponseEntity<List<LeaseResource>>(leaseResources, HttpStatus.OK);
//	}

//	@RequestMapping(method=RequestMethod.GET, value = "/leases")
//	public ResponseEntity<?> leases() {
//		List<Lease> leases = leaseRepository
//				.findLeasesByRenewalStartedAndRenewalCompletedAndWorkflowState(
//						true, false, "Confirm Renewal State");
//        
//		Resources<Lease> resources = new Resources<Lease>(leases); 
//
//        resources.add(linkTo(methodOn(LeaseController.class).leases()).withSelfRel()); 
//
//        // add other links as needed
//
//        return ResponseEntity.ok(resources);
//	}
	
    @RequestMapping(value="/updateNote", method=RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<HttpStatus> updateNote(     								   
    		@RequestParam(value = "leaseId") String leaseId,
    		@RequestParam(value = "note") String note)
    {
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		
    	Long id = Long.valueOf(leaseId);
    	Optional<Lease> lease = leaseRepository.findById(id);
    	if(lease==null)
    		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    	
    	lease.get().setNote(note);
    	leaseRepository.save(lease.get());
    	
		return re;
    }

    @RequestMapping(value="/signed", method=RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<HttpStatus> signed(     								   
    		@RequestParam(value = "leaseId") String leaseId,
    		@RequestParam(value = "signed") Boolean signed)
    {
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		
    	Long id = Long.valueOf(leaseId);
    	Optional<Lease> lease = leaseRepository.findById(id);
    	if(lease==null)
    		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    	
    	lease.get().setSigned(signed);
    	leaseRepository.save(lease.get());
    	
		return re;
    }

    @RequestMapping(value="/renewing", method=RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<HttpStatus> renewing(     								   
    		@RequestParam(value = "leaseId") String leaseId,
    		@RequestParam(value = "renewing", required = false) Boolean renewing)
    {
    	ResponseEntity<HttpStatus> re = new ResponseEntity<HttpStatus>(HttpStatus.OK);
		
    	Long id = Long.valueOf(leaseId);
    	Optional<Lease> lease = leaseRepository.findById(id);
    	if(lease==null)
    		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    	if(renewing != null)
    		lease.get().setRenewing(renewing);
    	
    	leaseRepository.save(lease.get());
    	
		return re;
    }
}
