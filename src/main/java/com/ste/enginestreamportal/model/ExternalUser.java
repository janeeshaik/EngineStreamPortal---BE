package com.ste.enginestreamportal.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "STEUser",uniqueConstraints={@UniqueConstraint(columnNames={"EmailAddress"}),@UniqueConstraint(columnNames={"UserId"})})
@JsonIgnoreProperties(allowGetters = true)
public class ExternalUser extends BaseEntity{

	private static final long serialVersionUID = 1L;

	/*
	 * private Date createdAt;
	 * private Date updatedAt;
	 * private Boolean status;
	 */


	@OneToOne
	@JoinColumn(name = "RoleId")
	private Role roleId;



	@Column(name = "Password")
	private String password;

	@Column(name = "Name")
	private String name;

	@Column(name = "EmailAddress")
	private String emailAddress;

	@Column(name="UserId")
	private String userid;

	@Column(name="PhoneNo")
	private String phoneNo;

	@Column(name = "PasswordLastUpdatedAt")
	private Date passwordLastUpdatedAt; 

	@Column(name = "InvalidAttemptsCount")
	private Integer invalidAttemptsCount;

	@OneToOne
	@JoinColumn(name = "DepartmentId")
	private Department departmentId;
	
	@Column(name = "BusinessTypes")
	private String businessTypes;


	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}


	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Date getPasswordLastUpdatedAt() {
		return passwordLastUpdatedAt;
	}

	public void setPasswordLastUpdatedAt(Date passwordLastUpdatedAt) {
		this.passwordLastUpdatedAt = passwordLastUpdatedAt;
	}

	public Integer getInvalidAttemptsCount() {
		return invalidAttemptsCount;
	}

	public void setInvalidAttemptsCount(Integer invalidAttemptsCount) {
		this.invalidAttemptsCount = invalidAttemptsCount;
	}

	
	public Department getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Department departmentId) {
		this.departmentId = departmentId;
	}

	public String getBusinessTypes() {
		return businessTypes;
	}

	public void setBusinessTypes(String businessTypes) {
		this.businessTypes = businessTypes;
	}

	@PrePersist
	protected void onCreate() {
		updatedAt = createdAt = new java.sql.Date(System.currentTimeMillis());
		//			updatedBy = createdBy = "1";
		status = false;
	}
}