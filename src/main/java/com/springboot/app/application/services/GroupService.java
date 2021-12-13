package com.springboot.app.application.services;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.app.application.dtos.GroupDto;
import com.springboot.app.application.dtos.RequestDto;
import com.springboot.app.application.dtos.ResponseDto;
import com.springboot.app.application.exceptions.DatabaseException;
import com.springboot.app.application.exceptions.InvalidParameter;
import com.springboot.app.application.exceptions.InvalidRequest;
import com.springboot.app.application.exceptions.NotFound;
import com.springboot.app.application.helpers.GeneratorResponseHelper;
import com.springboot.app.application.interfaces.GroupRepository;
import com.springboot.app.application.mappers.GroupMapper;
import com.springboot.app.domain.entities.Group;
import com.springboot.app.domain.validators.GroupValidator;

@Service
public class GroupService {
	
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private GroupValidator groupValidator;
	
	private GroupMapper groupMapper;
	private GeneratorResponseHelper<GroupDto> generateResponse;
	private GeneratorResponseHelper<Integer> generateResponseInt;
	
	
	public GroupService(GroupRepository groupRepository, 
			GroupValidator groupValidator) 
	{
		this.groupRepository = groupRepository;
		this.groupValidator = groupValidator;
		
		this.groupMapper = new GroupMapper();
		this.generateResponse = new GeneratorResponseHelper<GroupDto>();
		this.generateResponseInt = new GeneratorResponseHelper<Integer>();
	}
	
	public ResponseDto<GroupDto> getAllByUserId(RequestDto<GroupDto> request){
		if(request == null) 
			return this.generateResponse.fail(new InvalidRequest());
		
		var userId = request.tokenDetails.getId();
		var groups = new ArrayList<Group>();
		
		try {
			groups = (ArrayList<Group>) this.groupRepository.getAllByUserId(userId);
		}catch(SQLException ex) {
			return this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}
		
		var groupsDto = new ArrayList<GroupDto>();
		for(int i = 0; i < groups.size(); i++) {
			groupsDto.add(this.groupMapper.createDto(groups.get(i)));
		}
			
		return this.generateResponse.ok(groupsDto);
	}
	
	public ResponseDto<GroupDto> add(RequestDto<GroupDto> request) {
		if(request == null) 
			return this.generateResponse.fail(new InvalidRequest());
		
		var groupDto = request.body;
		var userId = request.tokenDetails.getId();
		
		var groupToAdd = this.groupMapper.createEntity(groupDto);
		groupToAdd.setUser(userId);
		
		if(!this.groupValidator.isValid(groupToAdd)) {
			var messageError = this.groupValidator.getMessageError(groupToAdd);
			return this.generateResponse.fail(new InvalidParameter(messageError));
		}
		
		Group groupAdded = null;
		try {
			groupAdded = this.groupRepository.add(groupToAdd);
		}catch(SQLException ex) {
			return this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}

		var groupArr = new ArrayList<GroupDto>();
		groupArr.add(this.groupMapper.createDto(groupAdded));
		return this.generateResponse.ok(groupArr);
	}
	
	public ResponseDto<GroupDto> update(RequestDto<GroupDto> request) {
		if(request == null)
			return this.generateResponse.fail(new InvalidRequest("The request is null!"));
		
		var groupDto = request.body;
		var userId = request.tokenDetails.getId();
		var groupToUpdate = this.groupMapper.createEntity(groupDto);
		groupToUpdate.setUser(userId);
		
		if(!this.groupValidator.isValid(groupToUpdate)) {
			var messageError = this.groupValidator.getMessageError(groupToUpdate);
			return this.generateResponse.fail(new InvalidParameter(messageError));
		}
		
		try {
			var groupInDb = this.groupRepository.getById(groupToUpdate.getId());
			if(groupInDb.getId() == -1 || groupInDb.getIdUser() != userId) 
				return this.generateResponse.fail(new NotFound("The group with id " + groupToUpdate.getId() + " does not exist!"));
			
			this.groupRepository.update(groupToUpdate);
		}catch(SQLException ex) {
			return this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}
			
		var groupArr = new ArrayList<GroupDto>();
		groupArr.add(groupDto);
		return this.generateResponse.ok(groupArr);	
	}
	
	public ResponseDto<Integer> delete(RequestDto<Integer> request) {
		if(request == null)
			return this.generateResponseInt.fail(new InvalidRequest("The request is null!"));
		
		var id = request.body;
		var idUser = request.tokenDetails.getId();
		if(id <= 0) return this.generateResponseInt.fail(new InvalidParameter("The id to delete must be superior than 0!"));
		
		try {
			var groupInDb = this.groupRepository.getById(id);
			if(groupInDb.getIdUser() != idUser) 
				return this.generateResponseInt.fail(new NotFound("The group with id " + id + " does not exist!"));
			
			this.groupRepository.delete(id);
		}catch(SQLException ex) {
			return this.generateResponseInt.fail(new DatabaseException(ex.getMessage()));
		}

		var groupArr = new ArrayList<Integer>();
		groupArr.add(id);
		return this.generateResponseInt.ok(groupArr);
	}
	
	
}
