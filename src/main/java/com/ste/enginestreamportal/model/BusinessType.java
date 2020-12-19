package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "BusinessType",uniqueConstraints={@UniqueConstraint(columnNames={"BusinessTypeName"})})
public class BusinessType extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "BusinessTypeName")
	@NotBlank
	private String businessTypeName;

	@Column(name = "BusinessTypeDesc")
	private String businessTypeDesc;

	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	public String getBusinessTypeDesc() {
		return businessTypeDesc;
	}

	public void setBusinessTypeDesc(String businessTypeDesc) {
		this.businessTypeDesc = businessTypeDesc;
	}
}
