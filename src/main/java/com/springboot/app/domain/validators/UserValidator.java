package com.springboot.app.domain.validators;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.springboot.app.domain.entities.BaseEntity;
import com.springboot.app.domain.entities.User;


@Component
public class UserValidator implements Validator{

	public UserValidator() {
		
	}
	
	@Override
	public boolean isValidWithoutCheckingId(BaseEntity entity) {
		var user = (User) entity;
		if(user == null) return false;
		if(!this.isUsernameValid(user.getUsername())) return false;
		if(!this.isEmailValid(user.getEmail())) return false;
		if(!this.isPasswordValid(user.getPassword())) return false;
		return true;
	}
	
	@Override
	public boolean isValid(BaseEntity entity) {
		var user = (User) entity;
		if(user == null) return false;
		if(!this.isValidWithoutCheckingId(entity)) return false;
		if(!this.isIdValid(user.getId())) return false;
		return true;
	}

	@Override
	public String getMessageError(BaseEntity entity) {
		var user = (User) entity;
		if(user == null) 
			return "The instance user is null!";
		if(!this.isUsernameValid(user.getUsername())) 
			return "The username must be between 2 and 100 characters, only numbers, lower letters and upper letters.";
		if(!this.isEmailValid(user.getEmail())) 
			return "The email is not valid!";
		if(!this.isPasswordValid(user.getPassword())) 
			return "The password must have 1 number, 1 capital letter, 1 lower letter and the length must be superior or equal to 8.";
		if(!this.isIdValid(user.getId())) 
			return "The id must be superior than 0";
		
		return "No errors found!";
	}
	
	private boolean isIdValid(int id) {
		return id > 0;
	}
	
	public boolean isUsernameValid(String username) {
		if(username == null) return false;
		return Pattern.compile("^[A-Za-z0-9]{2,100}$").matcher(username).find();
	}
	
	public boolean isEmailValid(String email) {
		if(email == null) return false;
		if(email.length() > 255 || email.length() < 10) return false;
		return Pattern.compile("^[a-z0-9_]{3,}@[a-z]{2,}\\.[a-z]{2,3}$").matcher(email).find();
	}
	
	public boolean isPasswordValid(String password) {
		if(password == null) return false;
		return Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[a-zA-Z0-9]{8,}$").matcher(password).find();
	}
}
