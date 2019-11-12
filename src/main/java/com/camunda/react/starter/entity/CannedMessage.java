package com.camunda.react.starter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.opencsv.bean.CsvBindByName;

@Entity(name="canned_message")
public class CannedMessage {

	public CannedMessage(){};
	public CannedMessage(String subject, String text, String html){
		this.subject = subject;
		this.text = text;
		this.html = html;
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "text")
    @CsvBindByName(required = true)
    private String subject;
    
    @Column(columnDefinition = "text")
    @CsvBindByName(required = true)
    private String text;
    
    @Column(columnDefinition = "text")
    @CsvBindByName(required = false)
    private String html;
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public long getId(){
    	return id;
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

}
