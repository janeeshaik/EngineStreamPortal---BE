package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="State")
public class State extends BaseEntity {

	private static final long serialVersionUID = 1L;

	
	@Column(name="StateName")
	private String stateName;


	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	
	
}
