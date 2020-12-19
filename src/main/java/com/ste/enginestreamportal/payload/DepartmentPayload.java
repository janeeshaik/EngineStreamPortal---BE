package com.ste.enginestreamportal.payload;

import com.ste.enginestreamportal.model.BaseEntity;

public class DepartmentPayload extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String departmentName;

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
