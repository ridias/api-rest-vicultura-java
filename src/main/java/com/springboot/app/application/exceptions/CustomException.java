package com.springboot.app.application.exceptions;

public abstract class CustomException {
	private String message;
	private int code;
	private String description;
	
	public CustomException(String message, int code, String description) {
		this.message = message;
		this.code = code;
		this.description = description;
	}
	
	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
}
