package com.springboot.app.application.exceptions;

public class InvalidToken extends CustomException {

	public InvalidToken() {
		super("The token is invalid!", 401, "Unauthorized: The token is invalid!");
	}
	
	public InvalidToken(String message) {
		super(message, 401, "Unauthorized: " + message);
	}

	
}
