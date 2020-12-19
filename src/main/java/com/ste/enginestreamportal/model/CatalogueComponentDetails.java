package com.ste.enginestreamportal.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "Catalogue_component_details")
public class CatalogueComponentDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (nullable = false, name = "Id")
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "component_code")
	private String componentCode;
	
	@Column(name = "component_parts")
	private String componentParts;

	@Column(name = "repair_sites")
	private String repairSites;
	
	@Column(name = "ata_repair_number")
	private String ataRepairNumber;
	
	@OneToMany(mappedBy = "catalogueComponentDetails")
	@JsonBackReference
	private List<CatalogueComponentValues> catalogueComponentValues;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComponentCode() {
		return componentCode;
	}

	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}

	public String getComponentParts() {
		return componentParts;
	}

	public void setComponentParts(String componentParts) {
		this.componentParts = componentParts;
	}

	public String getRepairSites() {
		return repairSites;
	}

	public void setRepairSites(String repairSites) {
		this.repairSites = repairSites;
	}

	public String getAtaRepairNumber() {
		return ataRepairNumber;
	}

	public void setAtaRepairNumber(String ataRepairNumber) {
		this.ataRepairNumber = ataRepairNumber;
	}
	

}