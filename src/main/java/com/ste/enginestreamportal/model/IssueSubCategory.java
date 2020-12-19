package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "IssueSubCategory")
public class IssueSubCategory extends BaseEntity {

	private static final long serialVersionUID = 1L;


	
	@Column(name="SubCategoryName")
	private String subCategoryName;

	
	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	
	
	
}
