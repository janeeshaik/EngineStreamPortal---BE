package com.ste.enginestreamportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserPasswords")
public class UserPasswords extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
@Column(name = "Password")
private String password;

@OneToOne
@JoinColumn(name = "UserId")
private User userId;

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public User getUserId() {
	return userId;
}

public void setUserId(User userId) {
	this.userId = userId;
}

}
