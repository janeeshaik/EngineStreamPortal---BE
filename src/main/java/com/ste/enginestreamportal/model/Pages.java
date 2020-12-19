package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Page",uniqueConstraints={@UniqueConstraint(columnNames={"PageName"})})
@JsonIgnoreProperties(allowGetters = true)
public class Pages extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@NotBlank
	@Column(name = "PageName")
	private String pageName;
	
	@NotBlank
	@Column(name = "PageNavigation")
	private String pageNavigation;
	
	@NotBlank
	@Column(name = "ParentId")
	private String parentId;
	

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageNavigation() {
		return pageNavigation;
	}

	public void setPageNavigation(String pageNavigation) {
		this.pageNavigation = pageNavigation;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}
