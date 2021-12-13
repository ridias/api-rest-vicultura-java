package com.springboot.app.domain.validators;

import com.springboot.app.domain.entities.BaseEntity;

public interface Validator {
	
	public boolean isValid(BaseEntity entity);
	public boolean isValidWithoutCheckingId(BaseEntity entity);
	public String getMessageError(BaseEntity entity);
}
