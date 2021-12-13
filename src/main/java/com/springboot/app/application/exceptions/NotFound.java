package com.springboot.app.application.exceptions;

public class NotFound extends CustomException {
	
	public NotFound() {
		super("Not found", 404, "Not found!");
	}
	
	public NotFound(String message) {
		super(message, 404, "Not found: " + message);
	}
}
