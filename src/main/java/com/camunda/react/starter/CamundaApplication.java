package com.camunda.react.starter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import java.util.logging.Logger;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.camunda.react.starter.AppConfigProperties;
import com.camunda.react.starter.LeaseRenewalScheduler;
import com.camunda.react.starter.LeaseUtil;
import com.camunda.react.starter.WorkflowUtil;
import com.camunda.react.starter.AppConfigProperties.Security;
import com.camunda.react.starter.entity.Lease;
import com.camunda.react.starter.repo.LeaseRepository;
import com.camunda.react.starter.repo.TenantRepository;
import com.camunda.react.starter.service.LeaseCleanupService;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@ComponentScan
@Configuration
@EnableConfigurationProperties(AppConfigProperties.class)
@EnableProcessApplication("spring-boot-starter")
public class CamundaApplication {
	
	public static Logger log = Logger.getLogger(CamundaApplication.class.getName());

	@Autowired
	AppConfigProperties config;
	
	public static void main(String... args) {
	    SpringApplication.run(CamundaApplication.class, args);
	}

	@Bean
	@Profile("schedule-renewal-start")
	public LeaseRenewalScheduler leaseRenewalScheduler(final LeaseRepository leaseRepository,
													   final RuntimeService runtimeService,
													   final TaskService taskService){
	
		return new LeaseRenewalScheduler() {
	
			@Override 
			@Scheduled(cron = "${app.cron.renewal-start}")
			public void run() {
				log.fine("[X] Running Lease renewal");
				//kicks off worklfow when the end date is 100 from current date
				Date leaseRenewalkickoffDate = WorkflowUtil.getKickOffDate(config.getCron().getRenewalKickoffBufferDays());
				log.fine("[X] Start date from today: "+leaseRenewalkickoffDate);
	
				List<Lease> leases = leaseRepository.findByEndDate(leaseRenewalkickoffDate);
				if (!leases.isEmpty()){
					for(Lease lease : leases){
						try {
							LeaseUtil.startLeaseRenewal(lease, leaseRepository, runtimeService, taskService, config);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}else{
					log.fine("[X] No leases found ending with kick off date: "+leaseRenewalkickoffDate);
				}
			}
		};
	}
	
	//TODO: refactor this is not scalable when there are many lease
	@Bean
	public LeaseRenewalScheduler updateLeaseState(final TaskService taskService,
											      final LeaseRepository leaseRepository) {
		
		return new LeaseRenewalScheduler() {
			
			@Override
			@Scheduled(cron = "0/10 * * * * ?")
			public void run() {
				List<Lease> leases = leaseRepository.findStarted();
				for (Lease lease : leases) {
					List<Task> tasks = WorkflowUtil.queryTasksById(taskService, lease.getProcessId());
			    	if (tasks != null && !tasks.isEmpty()){
						Task task = tasks.get(0);
						log.info("Found Task: "+task.getName());
						if (!task.getName().equalsIgnoreCase(lease.getWorkflowState())) {
							lease.setWorkflowState(task.getName());
							leaseRepository.save(lease);
							log.info("Updating Lease State with Task: "+task.getName());							
						}
			    	}
				}	
			}
		};
	}
	
	@Bean
	@Profile("schedule-renewal-clean")
	public LeaseRenewalScheduler scheduleCleanup(final LeaseCleanupService leaseCleanupService){
	
		return new LeaseRenewalScheduler() {
	
			@Override
			@Scheduled(cron = "${app.cron.renewal-clean}")
			public void run() {
				log.fine("[X] Start Lease Cleanup: "+Calendar.getInstance().getTime());
				leaseCleanupService.clean();
			}
		};
	}

	@Bean
	@Profile("init-roles")
	InitializingBean usersAndGroupsInitializer(final IdentityService identityService,
			final TenantRepository tenantRepository,
			final LeaseRepository leaseRepository,
			final RuntimeService runtimeService) {

	    return new InitializingBean() {
	        public void afterPropertiesSet() throws Exception {

	        	List<Security> securities = config.getSecurity();
	        	for(Security security : securities){
		        	List<String> roles = security.getRoles();
		        	for(String role : roles){
			            Group group = identityService.newGroup(role);
			            group.setName(role.toLowerCase()+"s");
			            group.setType("security-role");
			            identityService.saveGroup(group);
		        	}
		            User admin = identityService.newUser(security.getUsername());
		            admin.setPassword(security.getPassword());
		            identityService.saveUser(admin);
	        	}
	        }
	    };
	}


}
