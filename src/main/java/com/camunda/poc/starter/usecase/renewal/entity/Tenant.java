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
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name="tenant")
public class Tenant {

	public Tenant(){};
	public Tenant(String name, String email, String unitSlug, Lease lease){
		this.name = name;
		this.email = email;
		this.unitSlug = unitSlug;
		this.lease = lease;
	}
	
	public Tenant(String name, String email, String unitSlug){
		this.name = name;
		this.email = email;
		this.unitSlug = unitSlug;
	}
	public Tenant(String name, String email){
		this.name = name;
		this.email = email;
	}
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(nullable=true)
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    private Lease lease;
    public Lease getLease() { return lease; }   
    public void setLease(Lease lease){
    	this.lease = lease;
    }

    private String name;
    
    @Column(unique=true, nullable=false) 
    private String email;

    private String unitSlug;
        
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUnitSlug() {
		return unitSlug;
	}

	public void setUnitSlug(String unitSlug) {
		this.unitSlug = unitSlug;
	}

	public long getId() {
		return id;
	}

	public Date getCreated() {
		return created;
	}

	public Date getUpdated() {
		return updated;
	}

}
