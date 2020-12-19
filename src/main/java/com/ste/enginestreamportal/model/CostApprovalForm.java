package com.ste.enginestreamportal.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CostApprovalForm")
public class CostApprovalForm extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "ItemDescription")
	private String itemDescription;
	
	@Column(name = "Currency")
	private String currency;
	
	@Column(name = "EstimatedPrice")
	private BigDecimal estimatedPrice;
	
	@Column(name = "ExpiryDate")
	private Date expiryDate;
	
	@OneToOne
	@JoinColumn(name = "JobId")
	private JobDetails jobId;
	
	@OneToOne
	@JoinColumn(name = "RaisedBy")
	private User raisedBy;
	
	@OneToOne
	@JoinColumn(name = "Approver1")
	private User approver1;
	
	@OneToOne
	@JoinColumn(name = "Approver2")
	private User approver2;

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getEstimatedPrice() {
		return estimatedPrice;
	}

	public void setEstimatedPrice(BigDecimal estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public JobDetails getJobId() {
		return jobId;
	}

	public void setJobId(JobDetails jobId) {
		this.jobId = jobId;
	}

	public User getRaisedBy() {
		return raisedBy;
	}

	public void setRaisedBy(User raisedBy) {
		this.raisedBy = raisedBy;
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
}
