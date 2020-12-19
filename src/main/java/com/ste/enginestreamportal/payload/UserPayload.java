package com.ste.enginestreamportal.payload;

import com.ste.enginestreamportal.model.BaseEntity;


public class UserPayload extends BaseEntity {

	private static final long serialVersionUID = 1L;
	 
	private Long roleId;
	 
	private String password;
	 
	private String name;
	
	private String emailAddress;
	
	private String userid;
	
	private String phoneNo;
	
	private Long departmentId;
	
	private String businessTypes;

	public Long getRoleId() {
		return roleId;
	}


	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmailAddress() {
		return emailAddress;
	}


	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneno) {
		this.phoneNo = phoneno;
	}


	public Long getDepartmentId() {
		return departmentId;
	}


	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}


	public String getBusinessTypes() {
		return businessTypes;
	}


	public void setBusinessTypes(String businessTypes) {
		this.businessTypes = businessTypes;
	}
}
