package com.ste.enginestreamportal.payload;

import com.ste.enginestreamportal.model.BaseEntity;

public class RolePageMappingPayload extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private Long roleId;

	private String pageName;

	private Long pageId;

	private Boolean create;

	private Boolean view;

	private Boolean edit;

	private Boolean delete;
	
	private Boolean finance;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public Boolean getCreate() {
		return create;
	}

	public void setCreate(Boolean create) {
		this.create = create;
	}

	public Boolean getView() {
		return view;
	}

	public void setView(Boolean view) {
		this.view = view;
	}

	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public Boolean getDelete() {
		return delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	public Boolean getFinance() {
		return finance;
	}

	public void setFinance(Boolean finance) {
		this.finance = finance;
	}
	
}
