package com.springboot.app.application.services;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.app.application.dtos.RequestDto;
import com.springboot.app.application.dtos.ResponseDto;
import com.springboot.app.application.dtos.UserUpdateDto;
import com.springboot.app.application.dtos.UserUpdatePasswordDto;
import com.springboot.app.application.exceptions.DatabaseException;
import com.springboot.app.application.exceptions.InvalidCredentials;
import com.springboot.app.application.exceptions.InvalidParameter;
import com.springboot.app.application.exceptions.InvalidRequest;
import com.springboot.app.application.exceptions.NotFound;
import com.springboot.app.application.helpers.BCryptHelper;
import com.springboot.app.application.helpers.GeneratorResponseHelper;
import com.springboot.app.application.interfaces.UserRepository;
import com.springboot.app.domain.entities.User;
import com.springboot.app.domain.validators.UserValidator;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserValidator userValidator;
	
	private GeneratorResponseHelper<String> generateResponseStr;
	private GeneratorResponseHelper<UserUpdateDto> generateResponse;
	
	
	public UserService(UserRepository userRepository, UserValidator validator) {
		this.userRepository = userRepository;
		this.userValidator = validator;
		
		this.generateResponse = new GeneratorResponseHelper<UserUpdateDto>();
		this.generateResponseStr = new GeneratorResponseHelper<String>();	
	}
	
	public ResponseDto<UserUpdateDto> update(RequestDto<UserUpdateDto> request) {
		if(request == null)
			return this.generateResponse.fail(new InvalidRequest("The request is null!"));
		
		var userUpdateDto = request.body;
		int userId = request.tokenDetails.getId();
		
		try {
			var userToUpdateByEmail = this.userRepository.getByEmail(userUpdateDto.getEmail());
			var userToUpdateByUsername = this.userRepository.getByUsername(userUpdateDto.getUsername());
			
			if(userToUpdateByEmail.getId() != userId || userToUpdateByEmail.getId() == -1)
				return this.generateResponse.fail(new InvalidParameter("The email does not belong to this account!"));
			if(userToUpdateByUsername.getId() != userId || userToUpdateByUsername.getId() == -1)
				return this.generateResponse.fail(new InvalidParameter("The username does not belong to this account!"));
			
			if(!this.userValidator.isEmailValid(userUpdateDto.getEmail()) || !this.userValidator.isUsernameValid(userUpdateDto.getUsername())) 
				return this.generateResponse.fail(new InvalidParameter("The username or email are not valid!"));
			
			userToUpdateByEmail.setEmail(userUpdateDto.getEmail());
			userToUpdateByEmail.setUsername(userUpdateDto.getUsername());
			this.userRepository.update(userToUpdateByEmail);
		}catch(SQLException ex) {
			return this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}
		
		var arr = new ArrayList<UserUpdateDto>();
		arr.add(userUpdateDto);
		return this.generateResponse.ok(arr);
	}
	
	public ResponseDto<String> updatePassword(RequestDto<UserUpdatePasswordDto> request) {
		if(request == null)
			return this.generateResponseStr.fail(new InvalidRequest("The request is null!"));
		
		var userUpdatePasswordDto = request.body;
		var userId = request.tokenDetails.getId();
		
		try {
			User user = this.userRepository.getById(userId);
			if(user.getId() == -1)
				return this.generateResponseStr.fail(new NotFound("The user does not exist!"));
			
			if(!BCryptHelper.isHashedPasswordMatched(user.getPassword(), userUpdatePasswordDto.getCurrentPassowrd()))
				return this.generateResponseStr.fail(new InvalidCredentials("The current password does not match!"));
			
			user.setPassword(BCryptHelper.getHashedPassword(userUpdatePasswordDto.getNewPassword()));
			this.userRepository.updatePassword(user.getPassword(), user.getId());
		}catch(SQLException ex) {
			return this.generateResponseStr.fail(new DatabaseException(ex.getMessage()));
		}

		var arr = new ArrayList<String>();
		arr.add("DONE");
		return this.generateResponseStr.ok(arr);
	}
}
