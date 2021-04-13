package com.camunda.poc.starter.usecase.servicerequest.kafka.integration;

import org.springframework.context.annotation.Profile;

import java.util.Map;

@Profile("integration")
public class ServiceRequestEvent {

    private ServiceRequest serviceRequest;
    private String eventName;
    private String eventType;
    private Map<String, Object> eventParams;

    public ServiceRequestEvent(){}

    public ServiceRequestEvent(ServiceRequest serviceRequest){
        this.serviceRequest = serviceRequest;
    }

    public ServiceRequestEvent(Map eventParams){
        this.eventParams = eventParams;
    }

    public ServiceRequestEvent(ServiceRequest serviceRequest, Map eventParams){
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
