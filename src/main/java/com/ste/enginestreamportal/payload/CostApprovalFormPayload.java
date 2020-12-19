package com.ste.enginestreamportal.payload;

import java.math.BigDecimal;
import java.sql.Date;

import com.ste.enginestreamportal.model.BaseEntity;

public class CostApprovalFormPayload extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String itemDecription;
	
	private String currency;
	
	private BigDecimal estimatedPrice;
	
	private Date expiryDate;
	
	private Long jobId;
	
	private Long raisedBy;
	
	private Long approver1;
	
	private Long approver2;

	public String getItemDecription() {
		return itemDecription;
	}

	public void setItemDecription(String itemDecription) {
		this.itemDecription = itemDecription;
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

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Long getRaisedBy() {
		return raisedBy;
	}

	public void setRaisedBy(Long raisedBy) {
		this.raisedBy = raisedBy;
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

}
