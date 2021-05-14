package com.camunda.poc.starter.bpm;

//import com.camunda.poc.starter.usecase.order.entity.StockItem;
//import com.camunda.poc.starter.usecase.order.repo.StockItemRepository;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;


/**
 * This is an easy adapter implementation 
 * illustrating how a Java Delegate can be used 
 * from within a BPMN 2.0 Service Task.
 */
@Profile("exec")
@Component
public class ExternalTaskClientImpl {
 
  private final Logger LOGGER = Logger.getLogger(ExternalTaskClientImpl.class.getName());

//  private StockItemRepository repository;

  private ExecutorService fixedThreadPool;

//  @Autowired
//  public ExternalTaskClientImpl(StockItemRepository repository, ExecutorService fixedThreadPool) {
//    this.repository = repository;
//    this.fixedThreadPool = fixedThreadPool;
//  }

  private class ExternalTaskClientRunner implements Runnable {

    @Override
    public void run() {
      LOGGER.info("\n\n  ... " + ExternalTaskClientImpl.class.getName() + " invoked \n\n");

      ExternalTaskClient client = ExternalTaskClient.create()
              .baseUrl("http://localhost:8080/engine-rest")
              .asyncResponseTimeout(1000)
              .build();

      // subscribe to the topic
      client.subscribe("check-inventory-external-task")
              .handler((externalTask, externalTaskService) -> {

                boolean shortfall = false;
                List<Map<String, Object>> shortfallList = new ArrayList<>();
//                List<StockItem> list = repository.findStockItemByQuantityIsLessThan(10);
//                if (!list.isEmpty()) {
//                  shortfall = true;
//                  for (StockItem item : list) {
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("PmiCode", item.getPmiCode());
//                    map.put("PmiDescription", item.getPmiDescription());
//                    map.put("Quantity", item.getQuantity());
//                    map.put("Id", item.getId());
//                    shortfallList.add(map);
//                  }
//                }
                // add the invoice object and its id to a map
                Map<String, Object> variables = new HashMap<>();
                variables.put("shortfall", shortfall);
                variables.put("shortfallList", shortfallList);

                // select the scope of the variables
                externalTaskService.complete(externalTask, variables);

                System.out.println("\n\n The External Task " + externalTask.getId() +
                        " has been completed!");

              }).open();

    }
  }

  public void execute(){
    fixedThreadPool.submit(new ExternalTaskClientRunner());
  }
}