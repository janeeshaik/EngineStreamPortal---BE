package com.ste.enginestreamportal.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Material",uniqueConstraints={@UniqueConstraint(columnNames={"MaterialNumber"})})
public class Material extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "MaterialNumber")
	private String materialNumber;
	
	@Column(name = "MaterialDescription")
	private String materialDescription;
	
	@Column(name = "LastPOUnitPrice")
	private BigDecimal lastPOUnitPrice;
	
	@Column(name = "Last1stYearIssueQuantity")
	private Long last1stYearIssueQuantity;
	
	@Column(name = "Last2ndYearIssueQuantity")
	private Long last2ndYearIssueQuantity;
	
	@Column(name = "Last3rdYearIssueQuantity")
	private Long last3rdYearIssueQuantity;
	
	@ManyToOne
	@JoinColumn(name="Engine", referencedColumnName="id")
	@JsonBackReference
	private Engine engine;
	
	@ManyToOne
	@JoinColumn(name = "Plant", referencedColumnName="id")
	@JsonBackReference
	private Plant plant;

	@OneToMany(mappedBy = "material")
    private List<Batch> batches;
	
	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	public String getMaterialDescription() {
		return materialDescription;
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	public BigDecimal getLastPOUnitPrice() {
		return lastPOUnitPrice;
	}

	public void setLastPOUnitPrice(BigDecimal lastPOUnitPrice) {
		this.lastPOUnitPrice = lastPOUnitPrice;
	}

	public Long getLast1stYearIssueQuantity() {
		return last1stYearIssueQuantity;
	}

	public void setLast1stYearIssueQuantity(Long last1stYearIssueQuantity) {
		this.last1stYearIssueQuantity = last1stYearIssueQuantity;
	}

	public Long getLast2ndYearIssueQuantity() {
		return last2ndYearIssueQuantity;
	}

	public void setLast2ndYearIssueQuantity(Long last2ndYearIssueQuantity) {
		this.last2ndYearIssueQuantity = last2ndYearIssueQuantity;
	}

	public Long getLast3rdYearIssueQuantity() {
		return last3rdYearIssueQuantity;
	}

	public void setLast3rdYearIssueQuantity(Long last3rdYearIssueQuantity) {
		this.last3rdYearIssueQuantity = last3rdYearIssueQuantity;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public List<Batch> getBatches() {
		return batches;
	}

	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}
}
