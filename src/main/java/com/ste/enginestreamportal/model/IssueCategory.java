package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="IssueCategory")
public class IssueCategory extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	
	@Column(name="IssueCategoryTypeName")
	private String issueCategoryTypeName;

	
	public String getIssueCategoryTypeName() {
		return issueCategoryTypeName;
	}

	public void setIssueCategoryTypeName(String issueCategoryTypeName) {
		this.issueCategoryTypeName = issueCategoryTypeName;
	}

	

}
