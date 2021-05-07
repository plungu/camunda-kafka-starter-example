package com.camunda.poc.starter.bpm;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.spring.boot.starter.event.ExecutionEvent;
import org.camunda.bpm.spring.boot.starter.event.TaskEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

public class EventListenerBridge {

    @Component
    class MyListener {

        private final Logger LOGGER = Logger.getLogger(EventListenerBridge.class.getName());

        @EventListener
        public void onTaskEvent(DelegateTask taskDelegate) {
            // handle mutable task event
            LOGGER.info("\n\n  ... "+this.getClass().getName()+" invoked by "
                    + "processDefinitionId=" + taskDelegate.getProcessDefinitionId()
                    + ", activtyId=" + taskDelegate.getTaskDefinitionKey()
                    + ", activtyName='" + taskDelegate.getName() + "'"
                    + ", processInstanceId=" + taskDelegate.getProcessInstanceId()
                    + ", executionId=" + taskDelegate.getId()
                    + " \n\n");
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
}