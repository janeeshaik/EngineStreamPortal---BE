package com.ste.enginestreamportal.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SurplusFlagHistory")
public class SurplusFlagHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (nullable = false, name = "Id")
	private Long id;
	
	@Column(name = "FlagUpdated")
	private boolean flagUpdated;
	
	@Column(name = "Reason")
	private String reason;
	
	@Column(name = "UpdatedAt")
	private Date updatedAt;
	
	@Column(name = "UpdatedBy")
	private Long updatedBy;
	
	@ManyToOne
	@JoinColumn(name = "Batch", referencedColumnName = "id")
	@JsonBackReference
	private Batch batch;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isFlagUpdated() {
		return flagUpdated;
	}

	public void setFlagUpdated(boolean flagUpdated) {
		this.flagUpdated = flagUpdated;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Batch getBatch() {
		return batch;
	}

	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	
	
}
