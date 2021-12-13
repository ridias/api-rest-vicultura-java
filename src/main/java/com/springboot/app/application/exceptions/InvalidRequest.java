package com.springboot.app.application.exceptions;

public class InvalidRequest extends CustomException {

	public InvalidRequest() {
		super("Invalid request, the request is null!", 400, "Bad request: Invalid request, the request is null!");
	}
	
	public InvalidRequest(String message) {
		super(message, 400, "Bad request: " + message);
	}

}
