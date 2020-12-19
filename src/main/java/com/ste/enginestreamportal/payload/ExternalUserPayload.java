package com.ste.enginestreamportal.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExternalUserPayload  extends UserPayload{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String oldPassword;

	private String confirmPassword;


	public String getConfirmPassword() {
		return confirmPassword;
	}


	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}


	public String getOldPassword() {
		return oldPassword;
	}


	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


}
