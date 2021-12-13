package com.springboot.app.domain.validators;

import org.springframework.stereotype.Component;

import com.springboot.app.domain.entities.BaseEntity;
import com.springboot.app.domain.entities.Group;

@Component
public class GroupValidator implements Validator{
	
	public GroupValidator() {
		
	}
	
	@Override
	public boolean isValidWithoutCheckingId(BaseEntity entity) {
		var group = (Group) entity;
		if(group == null) return false;
		if(!this.isNameValid(group.getName())) return false;
		if(!this.isDescriptionValid(group.getDescription())) return false;
		if(!this.isUserIdValid(group.getIdUser())) return false;
		return true;
	}
	
	@Override
	public boolean isValid(BaseEntity entity) {
		var group = (Group) entity;
		if(group == null) return false;
		if(!this.isValidWithoutCheckingId(entity)) return false;
		if(!this.isIdValid(group.getId())) return false;
		return true;
	}

	@Override
	public String getMessageError(BaseEntity entity) {
		var group = (Group) entity;
		if(group == null) 
			return "The instance group is null!";
		if(!this.isNameValid(group.getName())) 
			return "The name is not valid, it should be between 2 and 100 characters and not null";
		if(!this.isDescriptionValid(group.getDescription())) 
			return "The description is not valid, it should be between 0 and 150 characters and not null";
		if(!this.isUserIdValid(group.getIdUser())) 
			return "The user id is not valid, it should be superior than 0";
		if(!this.isIdValid(group.getId())) 
			return "The id is not valid, it should be superior than 0";
		
		return "No errors found!";
	}
	
	
	private boolean isIdValid(int id) {
		return id > 0;
	}
	
	private boolean isNameValid(String name) {
		if(name == null) return false;
		return 2 <= name.length() && name.length() <= 100;
	}
	
	private boolean isDescriptionValid(String description) {
		if(description == null) return false;
		return 0 <= description.length() && description.length() <= 150;
	}
	
	private boolean isUserIdValid(int idUser) {
		return idUser > 0;
	}
}
