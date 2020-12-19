package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "RolePageMapping")
@JsonIgnoreProperties(allowGetters = true)
public class RolePageMapping extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "RoleId")
	private Long roleId;
	
	@OneToOne
	@JoinColumn(name = "PageId")
	private Pages pageId;
	
	
	@Column(name = "PageId", insertable = false, updatable = false)
	private Long pageIdAsLong;

	@Column(name = "Creates")
	private Boolean creates;
	
	@Column(name = "Views")
	private Boolean views;
	
	@Column(name = "Updates")
	private Boolean updates;
	
	@Column(name = "Deletes")
	private Boolean deletes;
	
	@Column(name = "Finance")
	private Boolean finance;

	
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Pages getPageId() {
		return pageId;
	}

	public void setPageId(Pages pageId) {
		this.pageId = pageId;
	}

	public Long getPageIdAsLong() {
		return pageIdAsLong;
	}

	public void setPageIdAsLong(Long pageIdAsLong) {
		this.pageIdAsLong = pageIdAsLong;
	}

	public Boolean getCreates() {
		return creates;
	}

	public void setCreates(Boolean creates) {
		this.creates = creates;
	}

	public Boolean getViews() {
		return views;
	}

	public void setViews(Boolean views) {
		this.views = views;
	}

	public Boolean getUpdates() {
		return updates;
	}

	public void setUpdates(Boolean updates) {
		this.updates = updates;
	}

	public Boolean getDeletes() {
		return deletes;
	}

	public void setDeletes(Boolean deletes) {
		this.deletes = deletes;
	}

	public Boolean getFinance() {
		return finance;
	}

	public void setFinance(Boolean finance) {
		this.finance = finance;
	}
	

	
}
