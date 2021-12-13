package com.springboot.app.domain.validators;

import org.springframework.stereotype.Component;

import com.springboot.app.domain.entities.BaseEntity;
import com.springboot.app.domain.entities.Material;

@Component
public class MaterialValidator implements Validator{
	
	public MaterialValidator() {
		
	}
	
	@Override
	public boolean isValidWithoutCheckingId(BaseEntity entity) {
		var material = (Material) entity;
		if(material == null) return false;
		if(!this.isNameValid(material.getName())) return false;
		if(!this.isYearValid(material.getYear())) return false;
		if(!this.isUrlImageValid(material.getUrlImage())) return false;
		if(!this.isUrlDetailsValid(material.getUrlDetails())) return false;
		return true;
	}
	
	@Override
	public boolean isValid(BaseEntity entity) {
		var material = (Material) entity;
		if(material == null) return false;
		if(!this.isValidWithoutCheckingId(entity)) return false;
		if(!this.isIdValid(material.getId())) return false;
		return true;
	}
	

	@Override
	public String getMessageError(BaseEntity entity) {
		var material = (Material) entity;
		if(material == null) 
			return "The instance material is null!";
		if(!this.isNameValid(material.getName())) 
			return "The name can't be null or it must be between 2 and 1024 characters!";
		if(!this.isYearValid(material.getYear())) 
			return "The year must be superior than 1800";
		if(!this.isUrlImageValid(material.getUrlImage())) 
			return "The url image is not valid!";
		if(!this.isUrlDetailsValid(material.getUrlDetails())) 
			return "The url details is not valid!";
		if(!this.isIdValid(material.getId())) 
			return "The id must be superior than 0!";
		
		return "No errors found!";
	}
	
	private boolean isIdValid(int id) {
		return id > 0;
	}
	
	private boolean isNameValid(String name) {
		if(name == null) return false;
		return 2 <= name.length() && name.length() <= 1024;
	}
	
	private boolean isYearValid(int year) {
		return year > 1800;
	}
	
	private boolean isUrlImageValid(String urlImage) {
		if(urlImage == null) return false;
		return 10 <= urlImage.length() && urlImage.length() <= 1024;
	}
	
	private boolean isUrlDetailsValid(String urlDetails) {
		if(urlDetails == null) return false;
		return 10 <= urlDetails.length() && urlDetails.length() <= 1024;
	}
	
	
}
