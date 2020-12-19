package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Catalogue_component_values")
public class CatalogueComponentValues {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (nullable = false, name = "Id")
	private Long id;
	
	@Column(name = "component_parts_value")
	private String componentPartsValue;
	
	@Column(name = "repair_sites_value")
	private String repairSitesValue;
	
	@Column(name = "ata_repair_number_value")
	private String ataRepairNumberValue;
	
	@Column(name = "repair_description")
	private String repairDescription;
	
	@Column(name = "tat_days")
	private String tatDays;
	
	@Column(name = "price")
	private String price;
	
	@ManyToOne
	@JoinColumn(name = "catalogue_component_details_id", referencedColumnName = "id")
	private CatalogueComponentDetails catalogueComponentDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComponentPartsValue() {
		return componentPartsValue;
	}

	public void setComponentPartsValue(String componentPartsValue) {
		this.componentPartsValue = componentPartsValue;
	}

	public String getRepairSitesValue() {
		return repairSitesValue;
	}

	public void setRepairSitesValue(String repairSitesValue) {
		this.repairSitesValue = repairSitesValue;
	}

	public String getAtaRepairNumberValue() {
		return ataRepairNumberValue;
	}

	public void setAtaRepairNumberValue(String ataRepairNumberValue) {
		this.ataRepairNumberValue = ataRepairNumberValue;
	}

	public String getRepairDescription() {
		return repairDescription;
	}

	public void setRepairDescription(String repairDescription) {
		this.repairDescription = repairDescription;
	}

	
	public String getTatDays() {
		return tatDays;
	}

	public void setTatDays(String tatDays) {
		this.tatDays = tatDays;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public CatalogueComponentDetails getCatalogueComponentDetails() {
		return catalogueComponentDetails;
	}

	public void setCatalogueComponentDetails(CatalogueComponentDetails catalogueComponentDetails) {
		this.catalogueComponentDetails = catalogueComponentDetails;
	}

	
	
	

}
