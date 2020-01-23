package com.camunda.poc.starter.usecase.renewal.csv;

import com.opencsv.bean.CsvBindByName;

public class Tenant {

    @CsvBindByName(required = false)
	private String name = "";
    
    @CsvBindByName(required = true)
	private String email;
		
	@CsvBindByName(required = true)
    private String propertySlug = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;	
	}

	public void setEmails(String email) {
		this.email = email;
	}

	public String getPropertySlug() {
		return propertySlug;
	}

	public void setPropertySlug(String unitSlug) {
		this.propertySlug = unitSlug;
	}
    
    
	
}
