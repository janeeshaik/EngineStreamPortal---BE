package com.ste.enginestreamportal.payload;

import com.ste.enginestreamportal.model.BaseEntity;

public class IssueSubCategoryPayload extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String subCategoryName;

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	
	
	
}
