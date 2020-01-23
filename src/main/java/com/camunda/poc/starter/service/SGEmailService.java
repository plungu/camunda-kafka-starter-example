package com.camunda.poc.starter.service;

public interface SGEmailService {

    void sendText(String from, String to, String subject, String body);

    void sendHTML(String from, String to, String subject, String body);

}