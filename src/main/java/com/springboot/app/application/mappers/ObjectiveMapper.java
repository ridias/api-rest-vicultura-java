package com.springboot.app.application.mappers;

import com.springboot.app.application.dtos.ObjectiveDto;
import com.springboot.app.domain.entities.Objective;

public class ObjectiveMapper {

	public Objective createEntity(ObjectiveDto dto) {
		return null;
	}
	
	public ObjectiveDto createDto(Objective objective) {
		var dto = new ObjectiveDto();
		dto.setId(objective.getId());
		dto.setMinProgress(objective.getMinProgress());
		dto.setMaxProgress(objective.getMaxProgress());
		dto.setCurrentProgress(objective.getCurrentProgress());
		dto.setNameMaterial(objective.getMaterial().getName());
		dto.setYearMaterial(objective.getMaterial().getYear());
		dto.setUrlImageMaterial(objective.getMaterial().getUrlImage());
		dto.setUrlDetailsMaterial(objective.getMaterial().getUrlDetails());
		dto.setIdGroup(objective.getIdGroup());
		return dto;
	}
}
