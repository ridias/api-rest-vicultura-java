package com.springboot.app.application.mappers;

import com.springboot.app.application.dtos.MaterialDto;
import com.springboot.app.domain.entities.Material;

public class MaterialMapper {
	
	public Material createEntity(MaterialDto dto) {
		var material = new Material();
		material.setId(dto.getId());
		material.setName(dto.getName());
		material.setYear(dto.getYear());
		material.setDateCreated(dto.getDateCreated());
		material.setUrlImage(dto.getUrlImage());
		material.setUrlDetails(dto.getUrlDetails());
		return material;
	}
	
	public MaterialDto createDto(Material material) {
		var dto = new MaterialDto();
		dto.setId(material.getId());
		dto.setName(material.getName());
		dto.setYear(material.getYear());
		dto.setDateCreated(material.getDateCreated());
		dto.setUrlImage(material.getUrlImage());
		dto.setUrlDetails(material.getUrlDetails());
		return dto;
	}
}
