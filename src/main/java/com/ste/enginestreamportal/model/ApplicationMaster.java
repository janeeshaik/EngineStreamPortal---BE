package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "ApplicationMaster",uniqueConstraints={@UniqueConstraint(columnNames={"Name"})})
public class ApplicationMaster  extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Description")
	private String description;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
