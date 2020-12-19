package com.ste.enginestreamportal.payload;

import com.ste.enginestreamportal.model.BaseEntity;

public class BusinessTypePayload extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String businessTypeName;
	
	private String businessTypeDesc;

	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	public String getBusinessTypeDesc() {
		return businessTypeDesc;
	}

	public void setBusinessTypeDesc(String businessTypeDesc) {
		this.businessTypeDesc = businessTypeDesc;
	}
}
