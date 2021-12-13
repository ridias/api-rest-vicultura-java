package com.springboot.app.application.mappers;

import com.springboot.app.application.dtos.UserLoginDto;
import com.springboot.app.domain.entities.User;

public class UserLoginMapper {
	
	public User createEntity(UserLoginDto userLogin) {
		var user = new User();
		user.setUsername(userLogin.getUsername());
		user.setPassword(userLogin.getPassword());
		return user;
	}
	
	public UserLoginDto createDto(User user) {
		return null;
	}
}
