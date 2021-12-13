package com.springboot.app.application.mappers;

import com.springboot.app.application.dtos.GroupDto;
import com.springboot.app.domain.entities.Group;

public class GroupMapper {
	
	public Group createEntity(GroupDto dto) {
		var group = new Group();
		group.setId(dto.getId());
		group.setName(dto.getName());
		group.setDescription(dto.getDescription());
		group.setDateCreated(dto.getDateCreated());
		group.setDateEnd(dto.getDateEnd());
		return group;
	}
	
	public GroupDto createDto(Group group) {
		var dto = new GroupDto();
		dto.setId(group.getId());
		dto.setName(group.getName());
		dto.setDescription(group.getDescription());
		dto.setDateCreated(group.getDateCreated());
		dto.setDateEnd(group.getDateEnd());
		return dto;
	}
}
