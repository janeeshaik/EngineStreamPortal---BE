package com.ste.enginestreamportal.payload;

import java.math.BigDecimal;
import java.sql.Date;

import com.ste.enginestreamportal.model.BaseEntity;
import com.ste.enginestreamportal.model.Certification;
import com.ste.enginestreamportal.model.Shipment;
import com.ste.enginestreamportal.model.User;

public class QuotationPayload extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private Long jobId;
	
	private BigDecimal materialHandlingCharges;
	
	private BigDecimal gstCharges;
	
	private String others;
	
	private BigDecimal totalCharges;
	
	private String ataNumber;
	
	private Integer tat;
	
	private Long certification;
	
	private Long shipping;
	
	private Date validityFrom;
	
	private Date validityTo;
	
	private String termsAndConditions;
	
	private Long preparedBy;
	
	private Long approvedBy;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public BigDecimal getMaterialHandlingCharges() {
		return materialHandlingCharges;
	}

	public void setMaterialHandlingCharges(BigDecimal materialHandlingCharges) {
		this.materialHandlingCharges = materialHandlingCharges;
	}

	public BigDecimal getGstCharges() {
		return gstCharges;
	}

	public void setGstCharges(BigDecimal gstCharges) {
		this.gstCharges = gstCharges;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public BigDecimal getTotalCharges() {
		return totalCharges;
	}

	public void setTotalCharges(BigDecimal totalCharges) {
		this.totalCharges = totalCharges;
	}

	public String getAtaNumber() {
		return ataNumber;
	}

	public void setAtaNumber(String ataNumber) {
		this.ataNumber = ataNumber;
	}

	public Integer getTat() {
		return tat;
	}

	public void setTat(Integer tat) {
		this.tat = tat;
	}

	public Long getCertification() {
		return certification;
	}

	public void setCertification(Long certification) {
		this.certification = certification;
	}

	public Long getShipping() {
		return shipping;
	}

	public void setShipping(Long shipping) {
		this.shipping = shipping;
	}

	public Date getValidityFrom() {
		return validityFrom;
	}

	public void setValidityFrom(Date validityFrom) {
		this.validityFrom = validityFrom;
	}

	public Date getValidityTo() {
		return validityTo;
	}

	public void setValidityTo(Date validityTo) {
		this.validityTo = validityTo;
	}

	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}

	public Long getPreparedBy() {
		return preparedBy;
	}

	public void setPreparedBy(Long preparedBy) {
		this.preparedBy = preparedBy;
	}

	public Long getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}	
}
