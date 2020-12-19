package com.ste.enginestreamportal.payload;

import com.ste.enginestreamportal.model.BaseEntity;

public class IssueCategoryPayload extends BaseEntity {

	private static final long serialVersionUID = 1L;

	
	private String  issueCategoryTypeName;


	public String getIssueCategoryTypeName() {
		return issueCategoryTypeName;
	}


	public void setIssueCategoryTypeName(String issueCategoryTypeName) {
		this.issueCategoryTypeName = issueCategoryTypeName;
	}
	

}
