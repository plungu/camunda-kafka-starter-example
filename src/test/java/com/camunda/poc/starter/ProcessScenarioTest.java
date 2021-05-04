package com.camunda.poc.starter;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.scenario.Scenario;
import org.camunda.bpm.scenario.run.ProcessRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.camunda.bpm.scenario.ProcessScenario;
//import org.camunda.bpm.scenario.Scenario;
//import org.camunda.bpm.scenario.run.ProcessRunner.ExecutableRunner;

import org.mockito.Mock;
//import static org.mockito.Matchers.*;
//import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
//import static org.junit.Assert.*;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class ProcessScenarioTest {

  private static final String PROCESS_DEFINITION_KEY = "spring-boot-starter";

  @Autowired
  private ProcessEngine processEngine;

  static {
    LogFactory.useSlf4jLogging(); // MyBatis
  }

  @Before
  public void setup() {
    init(processEngine);
    MockitoAnnotations.initMocks(this);
  }

  @Mock
  private ProcessScenario myProcess;

  @Test
  public void testHappyPath() {
    // Define scenarios by using camunda-bpm-assert-scenario:

    ProcessRunner.ExecutableRunner starter = Scenario.run(myProcess) //
        .startByKey(PROCESS_DEFINITION_KEY);

     Mockito.when(myProcess.waitsAtReceiveTask(ArgumentMatchers.anyString())).thenReturn((messageSubscription) -> {
      messageSubscription.receive();
     });
     Mockito.when(myProcess.waitsAtUserTask(ArgumentMatchers.anyString())).thenReturn((task) -> {
      task.complete();
     });

//     OK - everything prepared - let's go and execute the scenario
    Scenario scenario = starter.execute();

//     now you can do some assertions
    Mockito.verify(myProcess).hasFinished("EndEvent");
  }

}
