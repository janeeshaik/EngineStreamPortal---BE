package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Department",uniqueConstraints={@UniqueConstraint(columnNames={"DepartmentName"})})
public class Department extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "DepartmentName")
	@NotBlank
	private String departmentName;

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentTypeName) {
		this.departmentName = departmentTypeName;
	}
}
