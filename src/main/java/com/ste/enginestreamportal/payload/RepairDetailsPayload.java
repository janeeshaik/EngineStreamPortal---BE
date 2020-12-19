package com.ste.enginestreamportal.payload;

import java.math.BigDecimal;

import com.ste.enginestreamportal.model.BaseEntity;
import com.ste.enginestreamportal.model.Quotation;

public class RepairDetailsPayload extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String repairNo;
	
	private String repairDescription;
	
	private BigDecimal price;
	
	private Long quotationId;

	public String getRepairNo() {
		return repairNo;
	}

	public void setRepairNo(String repairNo) {
		this.repairNo = repairNo;
	}

	public String getRepairDescription() {
		return repairDescription;
	}

	public void setRepairDescription(String repairDescription) {
		this.repairDescription = repairDescription;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Long quotationId) {
		this.quotationId = quotationId;
	}
}
