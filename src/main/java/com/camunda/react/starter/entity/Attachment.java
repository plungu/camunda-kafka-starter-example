package com.camunda.react.starter.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Attachment {

    @Id
    private Long id;

    byte[] data;

	public Long getId() {
		return id;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
    
    
}
