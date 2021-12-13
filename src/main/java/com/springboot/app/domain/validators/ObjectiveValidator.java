package com.springboot.app.domain.validators;

import org.springframework.stereotype.Component;

import com.springboot.app.domain.entities.BaseEntity;
import com.springboot.app.domain.entities.Objective;

@Component
public class ObjectiveValidator implements Validator{
	
	public ObjectiveValidator() {
		
	}
	

	@Override
	public boolean isValidWithoutCheckingId(BaseEntity entity) {
		var objective = (Objective) entity;
		if(objective == null) return false;
		if(!this.isMinProgressValid(objective.getMinProgress())) return false;
		if(!this.isMaxProgressValid(objective.getMaxProgress())) return false;
		if(!this.isMinProgressLessThanMaxProgress(objective.getMinProgress(), objective.getMaxProgress())) return false;
		if(!this.isCurrentProgressValid(objective.getCurrentProgress(), objective.getMinProgress(), objective.getMaxProgress()))
			return false;
		if(!this.isIdGroupValid(objective.getIdGroup())) return false;
		if(!this.isIdMaterialValid(objective.getMaterial().getId())) return false;
		return true;
	}
	
	@Override
	public boolean isValid(BaseEntity entity) {
		var objective = (Objective) entity;
		if(objective == null) return false;
		if(!this.isValidWithoutCheckingId(entity)) return false;
		if(!this.isIdValid(objective.getId())) return false;
		return true;
	}

	@Override
	public String getMessageError(BaseEntity entity) {
		var objective = (Objective) entity;
		if(objective == null) 
			return "The instance objective is null!";
		if(!this.isMinProgressValid(objective.getMinProgress())) 
			return "The min progress must be superior or equal to 0!";
		if(!this.isMaxProgressValid(objective.getMaxProgress())) 
			return "The max progress must be superior than 0 and superior than min progress!";
		if(!this.isMinProgressLessThanMaxProgress(objective.getMinProgress(), objective.getMaxProgress())) 
			return "The min progress must be less than max progress!";
		if(!this.isCurrentProgressValid(objective.getCurrentProgress(), objective.getMinProgress(), objective.getMaxProgress()))
			return "The current progress must be between min and max progress!";
		if(!this.isIdGroupValid(objective.getIdGroup())) 
			return "The id group must be superior than 0!";
		if(!this.isIdMaterialValid(objective.getMaterial().getId())) 
			return "The id material must be superior than 0!";
		if(!this.isIdValid(objective.getId())) 
			return "The id must be superior than 0!";
		return "No errors found!";
	}
	
	private boolean isIdValid(int id) {
		return id > 0;
	}
	
	private boolean isMinProgressValid(int minProgress) {
		return minProgress >= 0;
	}
	
	private boolean isMaxProgressValid(int maxProgress) {
		return maxProgress > 0;
	}
	
	private boolean isMinProgressLessThanMaxProgress(int minProgress, int maxProgress) {
		return maxProgress > minProgress;
	}
	
	private boolean isCurrentProgressValid(int currentProgress, int minProgress, int maxProgress) {
		if(currentProgress < 0) return false;
		return minProgress <= currentProgress && currentProgress <= maxProgress;
	}
	
	private boolean isIdGroupValid(int idGroup) {
		return idGroup > 0;
	}
	
	private boolean isIdMaterialValid(int idMaterial) {
		return idMaterial > 0;
	}
}
