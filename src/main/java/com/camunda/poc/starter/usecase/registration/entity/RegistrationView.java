package com.camunda.poc.starter.usecase.registration.entity;

import java.io.Serializable;
import java.util.Date;

public class RegistrationView implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String registrationId;
	private String title;
	private String firstName;
	private String lastName;
	private String fullName;
	private Date created;
	private String createdBy;
	private Date updatedDate;
	private String businessKey;
	private String taskId;
	private String addedOrgName;
	private String addedOrgPartyNumber;
	private String newIuName;
	private String newIuNumber;
	private String newIuPrimOrgName;
	private String newIuPrimAccContFname;
	private String newIuPrimAccContLname;
	private String newIuPrimAccContPhone;
	private String newIuPrimAccContEmail;
	private String newIuPrimOrgPartyNumber;
	
	public String getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public String getAddedOrgName() {
		return addedOrgName;
	}
	public void setAddedOrgName(String addedOrgName) {
		this.addedOrgName = addedOrgName;
	}
	public String getAddedOrgPartyNumber() {
		return addedOrgPartyNumber;
	}
	public void setAddedOrgPartyNumber(String addedOrgPartyNumber) {
		this.addedOrgPartyNumber = addedOrgPartyNumber;
	}
	public String getNewIuName() {
		return newIuName;
	}
	public void setNewIuName(String newIuName) {
		this.newIuName = newIuName;
	}
	public String getNewIuNumber() {
		return newIuNumber;
	}
	public void setNewIuNumber(String newIuNumber) {
		this.newIuNumber = newIuNumber;
	}
	public String getNewIuPrimOrgName() {
		return newIuPrimOrgName;
	}
	public void setNewIuPrimOrgName(String newIuPrimOrgName) {
		this.newIuPrimOrgName = newIuPrimOrgName;
	}
	public String getNewIuPrimOrgPartyNumber() {
		return newIuPrimOrgPartyNumber;
	}
	public void setNewIuPrimOrgPartyNumber(String newIuPrimOrgPartyNumber) {
		this.newIuPrimOrgPartyNumber = newIuPrimOrgPartyNumber;
	}
	public String getNewIuPrimAccContFname() {
		return newIuPrimAccContFname;
	}
	public void setNewIuPrimAccContFname(String newIuPrimAccContFname) {
		this.newIuPrimAccContFname = newIuPrimAccContFname;
	}
	public String getNewIuPrimAccContLname() {
		return newIuPrimAccContLname;
	}
	public void setNewIuPrimAccContLname(String newIuPrimAccContLname) {
		this.newIuPrimAccContLname = newIuPrimAccContLname;
	}
	public String getNewIuPrimAccContPhone() {
		return newIuPrimAccContPhone;
	}
	public void setNewIuPrimAccContPhone(String newIuPrimAccContPhone) {
		this.newIuPrimAccContPhone = newIuPrimAccContPhone;
	}
	public String getNewIuPrimAccContEmail() {
		return newIuPrimAccContEmail;
	}
	public void setNewIuPrimAccContEmail(String newIuPrimAccContEmail) {
		this.newIuPrimAccContEmail = newIuPrimAccContEmail;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	
}
