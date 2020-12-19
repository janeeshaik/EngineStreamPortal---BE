package com.ste.enginestreamportal.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Quotation")
public class Quotation extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name = "JobId")
	private JobDetails jobId;
	
	@Column(name = "MaterialHandlingCharges")
	private BigDecimal materialHandlingCharges;
	
	@Column(name = "GstCharges")
	private BigDecimal gstCharges;
	
	@Column(name = "Others")
	private String others;
	
	@Column(name = "TotalCharges")
	private BigDecimal totalCharges;
	
	@Column(name = "AtaNumber")
	private String ataNumber;
	
	@Column(name = "Tat")
	private Integer tat;
	
	@OneToOne
	@JoinColumn(name = "Certification")
	private Certification certification;
	
	@OneToOne
	@JoinColumn(name = "Shipping")
	private Shipment shipping;
	
	@Column(name = "ValidityFrom")
	private Date validityFrom;
	
	@Column(name = "ValidityTo")
	private Date validityTo;
	
	@Column(name = "TermsAndConditions")
	private String termsAndConditions;
	
	@OneToOne
	@JoinColumn(name = "PreparedBy")
	private User preparedBy;
	
	@OneToOne
	@JoinColumn(name = "ApprovedBy")
	private User approvedBy;

	public JobDetails getJobId() {
		return jobId;
	}

	public void setJobId(JobDetails jobId) {
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

	public Certification getCertification() {
		return certification;
	}

	public void setCertification(Certification certification) {
		this.certification = certification;
	}

	public Shipment getShipment() {
		return shipping;
	}

	public void setShipment(Shipment shipping) {
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

	public User getPreparedBy() {
		return preparedBy;
	}

	public void setPreparedBy(User preparedBy) {
		this.preparedBy = preparedBy;
	}

	public User getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}
	

}
