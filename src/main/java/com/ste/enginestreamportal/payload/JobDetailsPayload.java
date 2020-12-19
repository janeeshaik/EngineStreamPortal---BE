package com.ste.enginestreamportal.payload;

import java.sql.Date;

import com.ste.enginestreamportal.model.BaseEntity;

public class JobDetailsPayload extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String jobNo;
	
	private String customer;
	
	private String customerPO;
	
	private String partDescription;
	
	private Date partReceivedDate;
	
	private String customerType;
	
	private String tsn;
	
	private String csn;

	private String formNo;

	private Date effectiveDate;

	private String reportNo;

	private String reason;

	private String work;

	private String recommendation;
	
	private String condition;
	
	private String findings;
	
	private String others;
	
	private Long approver1;
	
	private Long approver2;

	private Long approver3;

	private String jobCode;
	
	private Boolean conditionReportStatus;
	
	private Long assignedTo;

	public String getJobNo() {
		return jobNo;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCustomerPO() {
		return customerPO;
	}

	public void setCustomerPO(String customerPO) {
		this.customerPO = customerPO;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public Date getPartReceivedDate() {
		return partReceivedDate;
	}

	public void setPartReceivedDate(Date partReceivedDate) {
		this.partReceivedDate = partReceivedDate;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCsn() {
		return csn;
	}

	public void setCsn(String csn) {
		this.csn = csn;
	}

	public String getFormNo() {
		return formNo;
	}

	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getFindings() {
		return findings;
	}

	public void setFindings(String findings) {
		this.findings = findings;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public Long getApprover1() {
		return approver1;
	}

	public void setApprover1(Long approver1) {
		this.approver1 = approver1;
	}

	public Long getApprover2() {
		return approver2;
	}

	public void setApprover2(Long approver2) {
		this.approver2 = approver2;
	}

	public Long getApprover3() {
		return approver3;
	}

	public void setApprover3(Long approver3) {
		this.approver3 = approver3;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public Boolean getConditionReportStatus() {
		return conditionReportStatus;
	}

	public void setConditionReportStatus(Boolean conditionReportStatus) {
		this.conditionReportStatus = conditionReportStatus;
	}

	public Long getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getTsn() {
		return tsn;
	}

	public void setTsn(String tsn) {
		this.tsn = tsn;
	}	
}
