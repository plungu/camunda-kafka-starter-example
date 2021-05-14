package com.camunda.poc.starter.bpm;

import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * This is an easy adapter implementation
 * illustrating how a Java Delegate can be used
 * from within a BPMN 2.0 Service Task.
 */
@Profile("recruiter")
@Configuration
@ExternalTaskSubscription("check-legal-external-task")
public class ExternalTaskWroker implements ExternalTaskHandler {

  private final Logger LOGGER = Logger.getLogger(ExternalTaskWroker.class.getName());

//  private StockItemRepository repository;

//  @Autowired
//  public ExternalTaskWroker(StockItemRepository repository) {
//    this.repository = repository;
//  }

  @Override
  public void execute(ExternalTask externalTask,
                      ExternalTaskService externalTaskService) {
        // add your business logic here

      boolean shortfall = false;
      List<Map<String, Object>> shortfallList = new ArrayList<>();
//      List<StockItem> list = repository.findStockItemByQuantityIsLessThan(10);
//      if (!list.isEmpty()) {
//          shortfall = true;
//          for (StockItem item : list) {
//              Map<String, Object> map = new HashMap<>();
//              map.put("PmiCode", item.getPmiCode());
//              map.put("PmiDescription", item.getPmiDescription());
//              map.put("Quantity", item.getQuantity());
//              map.put("Id", item.getId());
//              shortfallList.add(map);
//          }
//      }
//      // add the invoice object and its id to a map
      Map<String, Object> variables = new HashMap<>();
      variables.put("shortfall", shortfall);
      variables.put("shortfallList", shortfallList);

      // select the scope of the variables
      externalTaskService.complete(externalTask, variables);

      System.out.println("\n\n The External Task " + externalTask.getId() +
              " has been completed!");

  }

}