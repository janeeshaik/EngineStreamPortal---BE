package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ConditionReportPartDetails")
public class ConditionReportPartDetails extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name = "PartId")
	private PartInfo partId;
	
	@Column(name = "Qty")
	private Integer quantity;
	
	@OneToOne
	@JoinColumn(name = "JobId")
	private JobDetails jobId;

	public PartInfo getPartId() {
		return partId;
	}

	public void setPartId(PartInfo partId) {
		this.partId = partId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public JobDetails getJobId() {
		return jobId;
	}

	public void setJobId(JobDetails jobId) {
		this.jobId = jobId;
	}

}
