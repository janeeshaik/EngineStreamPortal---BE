package com.ste.enginestreamportal.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Engine",uniqueConstraints={@UniqueConstraint(columnNames={"EngineType"})})
public class Engine extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "EngineType")
    private String engineType;
	
	@Column(name = "EngineTypeDescription")
	private String engineTypeDescription;

	@OneToMany(mappedBy = "engine")
	private List<Material> materials;
	

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getEngineTypeDescription() {
		return engineTypeDescription;
	}

	public void setEngineTypeDescription(String engineTypeDescription) {
		this.engineTypeDescription = engineTypeDescription;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}
}
