package com.ste.enginestreamportal.payload;

import java.math.BigDecimal;

import com.ste.enginestreamportal.model.BaseEntity;

public class QuotationPartDetailsPayload extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private Long conditionReportPartId;
	
	private BigDecimal expectedPrice;
	
	private Long quotationId;

	public Long getConditionReportPartId() {
		return conditionReportPartId;
	}

	public void setConditionReportPartId(Long conditionReportPartId) {
		this.conditionReportPartId = conditionReportPartId;
	}

	public BigDecimal getExpectedPrice() {
		return expectedPrice;
	}

	public void setExpectedPrice(BigDecimal expectedPrice) {
		this.expectedPrice = expectedPrice;
	}

	public Long getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Long quotationId) {
		this.quotationId = quotationId;
	}	
}
