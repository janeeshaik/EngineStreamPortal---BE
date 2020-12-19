package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Shipment", uniqueConstraints= {@UniqueConstraint(columnNames={"Name"})})
public class Shipment extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "Name")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
