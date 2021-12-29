package com.springboot.app.application.exceptions;

public class Forbidden extends CustomException {

	public Forbidden() {
		super("Forbidden", 403, "Forbidden!");
	}
	
	public Forbidden(String message) {
		super(message, 403, "Forbidden: " + message);
	}
}
