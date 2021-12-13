package com.springboot.app.application.exceptions;

public class DatabaseException extends CustomException {

	public DatabaseException() {
		super("It wasn't possible to get information to the database.", 500, "SQLException: It wasn't possible to get information to the database.");
	}
	
	public DatabaseException(String message) {
		super(message, 500, "SQLException: " + message);
	}
}
