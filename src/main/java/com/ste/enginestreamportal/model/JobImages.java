package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JobImages")
public class JobImages extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "Image")
	private String image;
	
	@OneToOne
	@JoinColumn(name = "JobId")
	private JobDetails jobId;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public JobDetails getJobId() {
		return jobId;
	}

	public void setJobId(JobDetails jobId) {
		this.jobId = jobId;
	}
	
	
}
