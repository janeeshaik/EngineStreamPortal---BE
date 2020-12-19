package com.ste.enginestreamportal.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "QuotationPartDetails")
public class QuotationPartDetails extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@OneToOne
	@JoinColumn(name = "ConditionReportPartId")
	private ConditionReportPartDetails conditionReportPartId;
	
	@Column(name = "ExpectedPrice")
	private BigDecimal expectedPrice;
	
	@OneToOne
	@JoinColumn(name = "QuotationId")
	private Quotation quotationId;

	public ConditionReportPartDetails getConditionReportPartId() {
		return conditionReportPartId;
	}

	public void setConditionReportPartId(ConditionReportPartDetails conditionReportPartId) {
		this.conditionReportPartId = conditionReportPartId;
	}

	public BigDecimal getExpectedPrice() {
		return expectedPrice;
	}

	public void setExpectedPrice(BigDecimal expectedPrice) {
		this.expectedPrice = expectedPrice;
	}

	public Quotation getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Quotation quotationId) {
		this.quotationId = quotationId;
	}
}
