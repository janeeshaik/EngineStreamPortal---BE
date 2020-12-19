package com.ste.enginestreamportal.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PartInfo",uniqueConstraints={@UniqueConstraint(columnNames={"PartNo"})})
public class PartInfo extends BaseEntity{

	private static final long serialVersionUID = 1L;

	
	@Column(name = "PartNo")
	private String partNo;
	
	@Column(name = "Description")
    private String description;
    
	@Column(name = "Price")
	private BigDecimal price;
	
	@Column(name = "Tat")
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
