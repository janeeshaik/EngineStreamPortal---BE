package com.ste.enginestreamportal.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "JobDetails", uniqueConstraints= {@UniqueConstraint(columnNames={"JobNo"})})
public class JobDetails extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "JobNo")
	private String jobNo;
	
	@Column(name = "Customer")
	private String customer;
	
	@Column(name = "CustomerPo")
	private String customerPO;
	
	@Column(name = "PartDescription")
	private String partDescription;
	
	@Column(name = "PartReceivedDate")
	private Date partReceivedDate;
	
	@Column(name = "CustomerType")
	private String customerType;
	
	@Column(name = "TSN")
	private String tsn;
	
	@Column(name = "CSN")
	private String csn;
	
	@Column(name = "FormNo")
	private String formNo;
	
	@Column(name = "EffectiveDate")
	private Date effectiveDate;
	
	@Column(name = "ReportNo")
	private String reportNo;
	
	@Column(name = "Reason")
	private String reason;
	
	@Column(name = "Work")
	private String work;
	
	@Column(name = "Recommendation")
	private String recommendation;
	
	@Column(name = "Condition")
	private String condition;
	
	@Column(name = "Findings")
	private String findings;
	
	@Column(name = "Others")
	private String others;
	
	@OneToOne
	@JoinColumn(name = "Approver1")
	private User approver1;
	
	@OneToOne
	@JoinColumn(name = "Approver2")
	private User approver2;
	
	@OneToOne
	@JoinColumn(name = "Approver3")
	private User approver3;
	
	@Column(name = "JobCode")
	private String jobCode;
	
	@Column(name = "ConditionReportStatus")
	private Boolean conditionReportStatus;
	
	@OneToOne
	@JoinColumn(name = "AssignedTo")
	private User assignedTo;

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

	public String getTsn() {
		return tsn;
	}

	public void setTsn(String tsn) {
		this.tsn = tsn;
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

	public User getApprover1() {
		return approver1;
	}

	public void setApprover1(User approver1) {
		this.approver1 = approver1;
	}

	public User getApprover2() {
		return approver2;
	}

	public void setApprover2(User approver2) {
		this.approver2 = approver2;
	}

	public User getApprover3() {
		return approver3;
	}

	public void setApprover3(User approver3) {
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

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}
}
