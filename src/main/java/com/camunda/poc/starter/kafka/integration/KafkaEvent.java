package com.camunda.poc.starter.kafka.integration;

import org.springframework.context.annotation.Profile;

import java.util.Map;

@Profile("integration")
public class KafkaEvent {

    public static String START_WORKFLOW_EVENT = "start-worflow-event";
    public static String UPDATE_WORKFLOW_EVENT = "update-worflow-event";
    public static String END_WORKFLOW_EVENT = "end-worflow-event";

    private KafkaRequestMapper kafkaRequestMapper;
    private String eventName;
    private String eventType;
    private Map<String, Object> eventParams;

    public KafkaEvent(){}

    public KafkaEvent(KafkaRequestMapper kafkaRequestMapper){
        this.kafkaRequestMapper = kafkaRequestMapper;
    }

    public KafkaEvent(Map eventParams){
        this.eventParams = eventParams;
    }

    public KafkaEvent(KafkaRequestMapper kafkaRequestMapper, Map eventParams){
        this.eventParams = eventParams;
        this.kafkaRequestMapper = kafkaRequestMapper;
    }

    public KafkaRequestMapper getKafkaRequestMapper() {
        return kafkaRequestMapper;
    }

    public void setKafkaRequestMapper(KafkaRequestMapper kafkaRequestMapper) {
        this.kafkaRequestMapper = kafkaRequestMapper;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Map getEventParams() {
        return eventParams;
    }

    public void setEventParams(Map eventParams) {
        this.eventParams = eventParams;
    }
}
