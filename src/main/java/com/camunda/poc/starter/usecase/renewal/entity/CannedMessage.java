package com.camunda.poc.starter.usecase.renewal.entity;

import com.opencsv.bean.CsvBindByName;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

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
