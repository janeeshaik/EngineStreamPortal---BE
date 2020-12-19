package com.ste.enginestreamportal.payload;

import java.math.BigDecimal;

import com.ste.enginestreamportal.model.BaseEntity;

public class DollarConversionsPayload extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private BigDecimal singaporeDollar;
	
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
