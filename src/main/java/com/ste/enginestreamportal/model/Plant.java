package com.ste.enginestreamportal.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Plant",uniqueConstraints={@UniqueConstraint(columnNames={"PlantCode"})})
public class Plant extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "PlantName")
	private String plantName;
	
	@Column(name = "PlantCode")
	private String plantCode;

	@OneToMany(mappedBy = "plant")
	private List<Material> materials;

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public String getPlantCode() {
		return plantCode;
	}

	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}
}