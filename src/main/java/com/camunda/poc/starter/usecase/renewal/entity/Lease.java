package com.camunda.poc.starter.usecase.renewal.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

//TODO: Decide if lombok Data should be used
//import lombok.Data;

@Entity(name="lease")
public class Lease implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -209110232715280386L;

	private @Version @JsonIgnore Long version;

	public Lease(){};
	public Lease(Date start, Date end, String property){
		this.start = start;
		this.end = end;
		this.property = property;
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

	public long getId() {
		return id;
	}
	
 	@Column(unique=true, nullable=false, name="property")
	private String property;
    public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy="lease")
	@LazyCollection(LazyCollectionOption.FALSE)
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	private List<Tenant> tenants;
    public List<Tenant> getTennants() { return tenants; }
    public void setTenants(List<Tenant> tenants){
    	this.tenants = tenants;
    }

    @OneToMany(cascade=CascadeType.ALL, mappedBy="lease")
	@LazyCollection(LazyCollectionOption.FALSE)
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @OrderBy("created DESC")
    private List<Message> messages;
    public List<Message> getMessages() { return messages; }
    public void setMessages(List<Message> messages){
    	this.messages = messages;
    }

//    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
//    @JoinColumn(name="id")
//    private List<CannedMessage> cannedMessages;
// 	public List<CannedMessage> getCannedMessages() {
//		return cannedMessages;
//	}
//	public void setCannedMessages(List<CannedMessage> cannedMessages) {
//		this.cannedMessages = cannedMessages;
//	}

	@Column(nullable=false, name="lease_start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Column(nullable=false, name="lease_end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @Column(nullable=true, name="lease_show_date")
		@Temporal(TemporalType.TIMESTAMP)
    private Date showDate;

    @Column(nullable=true)
    private Boolean renewalStarted;

    @Column(nullable=true)
    private Boolean renewing;

    @Column(nullable=true)
    private Boolean signed;

    @Column(nullable=true)
    private Boolean renewalCompleted;

    @Column(nullable=true)
    private Float currentRent;

    @Column(nullable=true)
    private Float oneYearOffer;

    @Column(nullable=true)
    private Float twoYearOffer;

    @Column(nullable=true)
    private String workflowState;

    @Column(nullable=true)
    private String processId;

	@Column(nullable=true, columnDefinition = "text")
    private String note;

 	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Boolean isRenewalStarted() {
		return renewalStarted;
	}
	public void setRenewalStarted(Boolean renewalStarted) {
		this.renewalStarted = renewalStarted;
	}
	public Boolean isRenewalCompleted() {
		return renewalCompleted;
	}
	public void setRenewalCompleted(Boolean renewalCompleted) {
		this.renewalCompleted = renewalCompleted;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public Date getCreated() {
		return created;
	}
	public Date getUpdated() {
		return updated;
	}
	public Float getCurrentRent() {
		return currentRent;
	}
	public void setCurrentRent(Float currentRent) {
		this.currentRent = currentRent;
	}
	public Date getShowDate() {
		return showDate;
	}
	public void setShowDate(Date showDate) {
		this.showDate = showDate;
	}
	public Float getOneYearOffer() {
		return oneYearOffer;
	}
	public void setOneYearOffer(Float oneYearOffer) {
		this.oneYearOffer = oneYearOffer;
	}
	public Float getTwoYearOffer() {
		return twoYearOffer;
	}
	public void setTwoYearOffer(Float twoYearOffer) {
		this.twoYearOffer = twoYearOffer;
	}
	public String getWorkflowState() {
		return workflowState;
	}
	public void setWorkflowState(String workflowState) {
		this.workflowState = workflowState;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Boolean isRenewing() {
		return renewing;
	}
	public void setRenewing(Boolean renewing) {
		this.renewing = renewing;
	}
	public Boolean isSigned() {
		return signed;
	}
	public void setSigned(Boolean signed) {
		this.signed = signed;
	}

}
