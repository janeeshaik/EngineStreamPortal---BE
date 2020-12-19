package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="Responses")
public class Responses extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Column(name = "Response")
	private String response;
	
	@ManyToOne
	@JoinColumn(name = "IssueId")
	@JsonBackReference
    private Issue issueId;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Issue getIssueId() {
		return issueId;
	}

	public void setIssueId(Issue issueId) {
		this.issueId = issueId;
	}	
}
