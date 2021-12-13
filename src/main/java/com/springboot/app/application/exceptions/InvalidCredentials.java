package com.springboot.app.application.exceptions;

public class InvalidCredentials extends CustomException {
	
	public InvalidCredentials() {
		super("The credentials are invalid!", 401, "Unauthorized: The credentials are invalid!");
	}
	
	public InvalidCredentials(String message) {
		super(message, 401, "Unauthorized: " + message);
	}
}
