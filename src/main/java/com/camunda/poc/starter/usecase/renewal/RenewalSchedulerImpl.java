package com.camunda.poc.starter.usecase.renewal;

import com.camunda.poc.starter.usecase.renewal.entity.Renewal;
import com.camunda.poc.starter.usecase.renewal.repo.CamundaRenewalTaskRepository;
import com.camunda.poc.starter.usecase.renewal.repo.RenewalRepository;
import com.camunda.poc.starter.usecase.renewal.repo.TenantRepository;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

//@Profile("renewal")
@Component
public class RenewalSchedulerImpl {

    public static Logger log = Logger.getLogger(Class.class.getName());

    AppConfigProperties config;

    @Autowired
    public RenewalSchedulerImpl(AppConfigProperties config){
        this.config = config;
    }

    @Bean
    @Profile("schedule-renewal-start")
    public RenewalScheduler leaseRenewalScheduler(final RenewalRepository renewalRepository,
                                                  final CamundaRenewalTaskRepository camundaRenewalTaskRepository,
                                                  final RuntimeService runtimeService){

        return new RenewalScheduler() {

            @Override
            @Scheduled(cron = "${app.cron.renewal-start}")
            public void run() {
                RenewalUtil.startRenewal(renewalRepository,
                                            camundaRenewalTaskRepository,
                                                runtimeService,
                                                        config);
            }
        };
    }

    //TODO: refactor this is not scalable when there are many renewal
    @Bean
    public RenewalScheduler updateRenewalState(final TaskService taskService,
                                             final RenewalRepository renewalRepository) {

        return new RenewalScheduler() {

            @Override
            @Scheduled(cron = "0/10 * * * * ?")
            public void run() {
                List<Renewal> renewals = renewalRepository.findStarted();
                for (Renewal renewal : renewals) {
                    List<Task> tasks = RenewalUtil.queryTasksByBizKey(taskService, renewal.getBusinessKey());
                    if (tasks != null && !tasks.isEmpty()){
                        Task task = tasks.get(0);
                        log.info("Found Task: "+task.getName());
                        if (!task.getName().equalsIgnoreCase(renewal.getWorkflowState())) {
                            renewal.setWorkflowState(task.getName());
                            renewalRepository.save(renewal);
                            log.info("Updating Renewal State with Task: "+task.getName());
                        }
                    }
                }
            }
        };
    }

    @Bean
    @Profile("schedule-renewal-clean")
    public RenewalScheduler scheduleCleanup(final RenewalCleanupService renewalCleanupService){

        return new RenewalScheduler() {

            @Override
            @Scheduled(cron = "${app.cron.renewal-clean}")
            public void run() {
                log.fine("[X] Start Renewal Cleanup: "+ Calendar.getInstance().getTime());
                renewalCleanupService.clean();
            }
        };
    }

    @Bean
    @Profile("init-roles")
    InitializingBean usersAndGroupsInitializer(final IdentityService identityService,
                                               final TenantRepository tenantRepository,
                                               final RenewalRepository renewalRepository,
                                               final RuntimeService runtimeService) {

        return new InitializingBean() {
            public void afterPropertiesSet() throws Exception {

                List<AppConfigProperties.Security> securities = config.getSecurity();
                for(AppConfigProperties.Security security : securities){
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
