package com.springboot.app.application.mappers;

import com.springboot.app.application.dtos.UserRegisterDto;
import com.springboot.app.domain.entities.User;

public class UserRegisterMapper {

	public User createEntity(UserRegisterDto userDto) {
		var user = new User();
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		user.setDateCreated(userDto.getDateCreated());
		return user;
	}
	
	public UserRegisterDto createDto(User user) {
		return null;
	}
}
