package com.ste.enginestreamportal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthFailException extends RuntimeException {
	private String resourceName;
	private String fieldName;
	private Object fieldValue;

	public AuthFailException(String resourceName, String fieldName, Object fieldValue) {
		super("Userid or Password Invalid");
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public Object getFieldValue() {
		return fieldValue;
	}
}