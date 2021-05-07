package com.camunda.poc.starter.kafka.integration;

import org.springframework.context.annotation.Profile;

import java.util.Map;

@Profile("integration")
public class KafkaEvent {

    private ServiceRequest serviceRequest;
    private String eventName;
    private String eventType;
    private Map<String, Object> eventParams;

    public KafkaEvent(){}

    public KafkaEvent(ServiceRequest serviceRequest){
        this.serviceRequest = serviceRequest;
    }

    public KafkaEvent(Map eventParams){
        this.eventParams = eventParams;
    }

    public KafkaEvent(ServiceRequest serviceRequest, Map eventParams){
        this.eventParams = eventParams;
        this.serviceRequest = serviceRequest;
    }

    public ServiceRequest getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(ServiceRequest serviceRequest) {
        this.serviceRequest = serviceRequest;
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
