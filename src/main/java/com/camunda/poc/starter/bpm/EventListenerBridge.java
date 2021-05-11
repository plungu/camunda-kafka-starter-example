package com.camunda.poc.starter.bpm;

import com.camunda.poc.starter.kafka.integration.KafkaEventChannels;
import com.camunda.poc.starter.repo.PolicyRepository;
import com.camunda.poc.starter.repo.ServiceRequestRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.spring.boot.starter.event.ExecutionEvent;
import org.camunda.bpm.spring.boot.starter.event.TaskEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class EventListenerBridge {


    private KafkaEventChannels channels;
    private PolicyRepository repository;

    @Autowired
    public EventListenerBridge(KafkaEventChannels source, PolicyRepository repository){
        this.channels = source;
        this.repository = repository;
    }


    private final Logger LOGGER = Logger.getLogger(EventListenerBridge.class.getName());

    @EventListener(condition="#taskDelegate.eventName=='create'")
    public void onTaskEvent(DelegateTask taskDelegate) {
        // handle mutable task event
        LOGGER.info("\n\n  ... "+this.getClass().getName()+" invoked by "
                + "processDefinitionId=" + taskDelegate.getProcessDefinitionId()
                + ", activtyId=" + taskDelegate.getTaskDefinitionKey()
                + ", activtyName='" + taskDelegate.getName() + "'"
                + ", processInstanceId=" + taskDelegate.getProcessInstanceId()
                + ", executionId=" + taskDelegate.getId()
                + " \n\n");

        channels.publish().send(MessageBuilder.withPayload("{\"CAMUNDA-POC-EVENT\": \"CAMUNDA POC TEST EVENT\"}").build());
    }

    @EventListener
    public void onTaskEvent(TaskEvent taskEvent) {
        // handle immutable task event
        LOGGER.info("\n\n  ... "+this.getClass().getName()+" invoked by "
                + "processDefinitionId=" + taskEvent.getProcessDefinitionId()
                + ", activtyId=" + taskEvent.getTaskDefinitionKey()
                + ", activtyName='" + taskEvent.getName() + "'"
                + ", processInstanceId=" + taskEvent.getProcessInstanceId()
                + ", executionId=" + taskEvent.getId()
                + " \n\n");
    }

    @EventListener
    public void onExecutionEvent(DelegateExecution executionDelegate) {
        // handle mutable execution event
        LOGGER.info("\n\n  ... "+this.getClass().getName()+" invoked by "
                + "processDefinitionId=" + executionDelegate.getProcessDefinitionId()
                + ", processInstanceId=" + executionDelegate.getProcessInstanceId()
                + ", executionId=" + executionDelegate.getId()
                + " \n\n");
    }

    @EventListener
    public void onExecutionEvent(ExecutionEvent executionEvent) {
        // handle immutable execution event
        LOGGER.info("\n\n  ... "+this.getClass().getName()+" invoked by "
                + "processDefinitionId=" + executionEvent.getProcessDefinitionId()
                + ", processInstanceId=" + executionEvent.getProcessInstanceId()
                + ", executionId=" + executionEvent.getId()
                + " \n\n");
    }

    @EventListener
    public void onHistoryEvent(HistoryEvent historyEvent) {
        // handle history event
        LOGGER.info("\n\n  ... "+this.getClass().getName()+" invoked by "
                + "processDefinitionId=" + historyEvent.getProcessDefinitionId()
                + ", processInstanceId=" + historyEvent.getProcessInstanceId()
                + ", executionId=" + historyEvent.getId()
                + " \n\n");
    }
}