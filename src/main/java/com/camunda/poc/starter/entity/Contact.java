package com.camunda.poc.starter.entity;

public class Contact {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String businessKey;
    public String getBusinessKey() { return businessKey; }
    public void setBusinessKey(String businessKey) { this.businessKey = businessKey; }

    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String toString() {
        return this.name + ", " + this.businessKey;
    }

    public Boolean hasEmail() {
        if(email.isEmpty() || !email.contains("@")) {
            return false;
        }else{
            return true;
        }
    }

}
