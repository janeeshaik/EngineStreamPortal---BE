package com.ste.enginestreamportal.payload;

import com.ste.enginestreamportal.model.BaseEntity;

public class JobImagesPayload extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String image;
	
	private Long jobId;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	
}
