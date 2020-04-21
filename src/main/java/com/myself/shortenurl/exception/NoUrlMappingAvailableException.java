package com.myself.shortenurl.exception;

public class NoUrlMappingAvailableException extends Exception {

	private static final long serialVersionUID = -3649742199618424410L;
	public NoUrlMappingAvailableException() {
		
	}
public NoUrlMappingAvailableException(String message) {
		super(message);
	}
}
