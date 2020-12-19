package com.ste.enginestreamportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "STEUser")
@JsonIgnoreProperties(allowGetters = true)
public class Login {

	@Id
	private Long Id;
	
	@Column(name ="UserId")
	private String userName;

	@NotBlank
	private String password;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String p) {
		password = p;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
