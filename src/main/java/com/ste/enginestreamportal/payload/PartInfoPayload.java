package com.ste.enginestreamportal.payload;

import java.math.BigDecimal;

import com.ste.enginestreamportal.model.BaseEntity;

public class PartInfoPayload extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String partNo;

	private String description;
    
	private BigDecimal price;
	
	private Integer tat;

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getTat() {
		return tat;
	}

	public void setTat(Integer tat) {
		this.tat = tat;
	}
}
