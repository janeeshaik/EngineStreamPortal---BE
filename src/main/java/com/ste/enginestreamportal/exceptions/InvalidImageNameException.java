package com.ste.enginestreamportal.exceptions;

public class InvalidImageNameException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidImageNameException(String message) {
		super(message);
	}
	
	public InvalidImageNameException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
