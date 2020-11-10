package com.camunda.poc.starter.usecase.renewal.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity(name="camunda_renewal_task")
public class CamundaRenewalTask {

	public CamundaRenewalTask(){};
	public CamundaRenewalTask(long renewalId, String taskId){
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable=false)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    private Renewal renewal;
    public Renewal getRenewal() { return renewal; }
    public void setRenewal(Renewal renewal){
        this.renewal = renewal;
    }

    @ManyToOne
    @JoinColumn(nullable=false)
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    private CamundaTask task;
    public CamundaTask getTask() { return task; }
    public void setTask(CamundaTask task){
        this.task = task;
    }

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public long getId(){
    	return id;
    }
}
