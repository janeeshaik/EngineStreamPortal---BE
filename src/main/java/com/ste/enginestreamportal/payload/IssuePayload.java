package com.ste.enginestreamportal.payload;


import java.sql.Date;

import com.ste.enginestreamportal.model.BaseEntity;

public class IssuePayload extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Long state;
	
	private String issueNumber;
	
	private Date startDate;
	
	private Date endDate;
	
	private String description;
	
	private String engineSerialNumber;
	
	private Long customerId;
	
	private Long issueCategoryId;
	
	private Long issueSubCategoryId;
	
    private String subject;

    private Long raisedBy;

    private Long raisedFor;

    private Date raisedDate;

    private Date responseRequiredDate;

    private Long raisedForDepartmentId;

    private Date issueEndDate;

    private Integer excusableDelay;

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEngineSerialNumber() {
		return engineSerialNumber;
	}

	public void setEngineSerialNumber(String engineSerialNumber) {
		this.engineSerialNumber = engineSerialNumber;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getIssueCategoryId() {
		return issueCategoryId;
	}

	public void setIssueCategoryId(Long issueCategoryId) {
		this.issueCategoryId = issueCategoryId;
	}

	public Long getIssueSubCategoryId() {
		return issueSubCategoryId;
	}

	public void setIssueSubCategoryId(Long issueSubCategoryId) {
		this.issueSubCategoryId = issueSubCategoryId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getRaisedBy() {
		return raisedBy;
	}

	public void setRaisedBy(Long raisedBy) {
		this.raisedBy = raisedBy;
	}

	public Long getRaisedFor() {
		return raisedFor;
	}

	public void setRaisedFor(Long raisedFor) {
		this.raisedFor = raisedFor;
	}

	public Date getRaisedDate() {
		return raisedDate;
	}

	public void setRaisedDate(Date raisedDate) {
		this.raisedDate = raisedDate;
	}

	public Date getResponseRequiredDate() {
		return responseRequiredDate;
	}

	public void setResponseRequiredDate(Date responseRequiredDate) {
		this.responseRequiredDate = responseRequiredDate;
	}

	public Long getRaisedForDepartmentId() {
		return raisedForDepartmentId;
	}

	public void setRaisedForDepartmentId(Long raisedForDepartmentId) {
		this.raisedForDepartmentId = raisedForDepartmentId;
	}

	public Date getIssueEndDate() {
		return issueEndDate;
	}

	public void setIssueEndDate(Date issueEndDate) {
		this.issueEndDate = issueEndDate;
	}

	public Integer getExcusableDelay() {
		return excusableDelay;
	}

	public void setExcusableDelay(Integer excusableDelay) {
		this.excusableDelay = excusableDelay;
	}
}
