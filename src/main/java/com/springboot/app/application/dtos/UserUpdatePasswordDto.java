package com.springboot.app.application.dtos;

public class UserUpdatePasswordDto {
	
	private String currentPassowrd;
	private String newPassword;
	
	public String getCurrentPassowrd() {
		return currentPassowrd;
	}
	public void setCurrentPassowrd(String currentPassowrd) {
		this.currentPassowrd = currentPassowrd;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
