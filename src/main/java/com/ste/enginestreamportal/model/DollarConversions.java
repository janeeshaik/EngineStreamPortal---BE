package com.ste.enginestreamportal.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DollarConversions")
public class DollarConversions extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "Sd")
	private BigDecimal singaporeDollar;
	
	@Column(name = "Usd")
	private BigDecimal usDollar;

	public BigDecimal getSingaporeDollar() {
		return singaporeDollar;
	}

	public void setSingaporeDollar(BigDecimal singaporeDollar) {
		this.singaporeDollar = singaporeDollar;
	}

	public BigDecimal getUsDollar() {
		return usDollar;
	}

	public void setUsDollar(BigDecimal usDollar) {
		this.usDollar = usDollar;
	}
	
	
	
}
