package com.ste.enginestreamportal.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Role",uniqueConstraints={@UniqueConstraint(columnNames={"RoleName"})})
@JsonIgnoreProperties(allowGetters = true)
public class Role extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@NotBlank
	@Column(name = "RoleName")
	private String roleName;
	
	@NotBlank
	@Column(name = "RoleDescription")
	private String roleDescription;
	
	@Column(name = "Applications")
	private String applications;
	

	@OneToMany(mappedBy = "roleId")
	private List<RolePageMapping> rolePageMapping;
	
	
	public List<RolePageMapping> getRolePageMapping() {
		return rolePageMapping;
	}

	public void setRolePageMapping(List<RolePageMapping> rolePageMapping) {
		this.rolePageMapping = rolePageMapping;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getApplications() {
		return applications;
	}

	public void setApplications(String applications) {
		this.applications = applications;
	}	
	
}
