package com.springboot.app.application.dtos;

public class UserUpdateDto {

	private String username;
	private String email;
	
	public UserUpdateDto() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
