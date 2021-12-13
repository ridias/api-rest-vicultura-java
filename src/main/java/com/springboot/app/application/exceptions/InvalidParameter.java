package com.springboot.app.application.exceptions;

public class InvalidParameter extends CustomException{
	
	public InvalidParameter() {
		super("A parameter is invalid", 400, "Bad request: A parameter is invalid");
	}
	
	public InvalidParameter(String message) {
		super(message, 400, "Bad request: " + message);
	}
}
