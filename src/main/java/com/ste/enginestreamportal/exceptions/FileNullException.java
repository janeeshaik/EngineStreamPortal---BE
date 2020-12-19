package com.ste.enginestreamportal.exceptions;

public class FileNullException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public FileNullException(String message) {
		super(message);
	}
	public FileNullException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
