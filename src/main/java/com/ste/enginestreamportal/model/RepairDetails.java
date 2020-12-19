package com.ste.enginestreamportal.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "RepairDetails",uniqueConstraints={@UniqueConstraint(columnNames={"RepairNo"})})
public class RepairDetails extends BaseEntity{

	private static final long serialVersionUID = 1L;

	
	@Column(name = "RepairNo")
	private String repairNo;
	
	@Column(name = "RepairDescription")
	private String repairDescription;
	
	@Column(name = "Price")
	private BigDecimal price;
	
	@OneToOne
	@JoinColumn(name = "QuotationId")
	private Quotation quotationId;

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

	public Quotation getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(Quotation quotationId) {
		this.quotationId = quotationId;
	}
	
	

}
