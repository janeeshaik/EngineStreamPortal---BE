package com.ste.enginestreamportal.payload;

import com.ste.enginestreamportal.model.BaseEntity;

public class ResponsesPayload extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String response;
	
	private Long issueId;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}
}
