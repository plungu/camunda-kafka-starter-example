package com.camunda.poc.starter.usecase.renewal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name="message")
public class Message {

	public Message(){};
	public Message(String sender, String recipient, String subject, String text, String html, Renewal renewal){
		this.sender = sender;
		this.recipient = recipient;
		this.subject = subject;
		this.text = text;
		this.html = html;
		this.renewal = renewal;
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable=false)
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    private Renewal renewal;
    public Renewal getRenewal() { return renewal; }   
    public void setRenewal(Renewal renewal){
    	this.renewal = renewal;
    }

 	@Column(nullable=false) 
    private String sender;

 	@Column(nullable=false) 
    private String recipient;

    private String subject;
    
    @Column(columnDefinition = "text")
    private String text;
    
    @Column(columnDefinition = "text")
    private String html;
    
    private Boolean archived;
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public long getId(){
    	return id;
    }
    
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Boolean isArchived() {
		return archived;
	}
	public void setArchived(Boolean archived) {
		this.archived = archived;
	}
	public Date getCreated() {
		return created;
	}

}
