package com.springboot.app.application.services;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.app.application.dtos.ObjectiveDto;
import com.springboot.app.application.dtos.RequestDto;
import com.springboot.app.application.dtos.RequestPaginationDto;
import com.springboot.app.application.dtos.ResponseDto;
import com.springboot.app.application.dtos.ResponsePaginationDto;
import com.springboot.app.application.exceptions.DatabaseException;
import com.springboot.app.application.exceptions.Forbidden;
import com.springboot.app.application.exceptions.InvalidParameter;
import com.springboot.app.application.exceptions.InvalidRequest;
import com.springboot.app.application.exceptions.NotFound;
import com.springboot.app.application.helpers.GeneratorResponseHelper;
import com.springboot.app.application.interfaces.GroupRepository;
import com.springboot.app.application.interfaces.ObjectiveRepository;
import com.springboot.app.application.mappers.ObjectiveMapper;
import com.springboot.app.domain.entities.Objective;
import com.springboot.app.domain.validators.ObjectiveValidator;

@Service
public class ObjectiveService {
	
	@Autowired
	private ObjectiveRepository objectiveRepository;
	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private ObjectiveValidator objectiveValidator;
	
	private ObjectiveMapper objectiveMapper;
	private GeneratorResponseHelper<ObjectiveDto> generateResponse;
	private GeneratorResponseHelper<Integer> generateResponseInt;
	
	public ObjectiveService(ObjectiveRepository objectiveRepository, GroupRepository groupRepository, ObjectiveValidator validator) {
		this.objectiveRepository = objectiveRepository;
		this.groupRepository = groupRepository;
		this.objectiveValidator = validator;
		
		this.objectiveMapper = new ObjectiveMapper();
		this.generateResponse = new GeneratorResponseHelper<ObjectiveDto>();
		this.generateResponseInt = new GeneratorResponseHelper<Integer>();
	}
	
	public ResponsePaginationDto<ObjectiveDto> getAllByIdGroup(RequestPaginationDto<Integer> request){
		if(request == null)
			return (ResponsePaginationDto<ObjectiveDto>) this.generateResponse.fail(new InvalidRequest("The request is null!"));
		
		var userTokenDetails = request.tokenDetails;
		var idUser = userTokenDetails.getId();
		var idGroup = request.body;
		
		try {
			var groupInDb = this.groupRepository.getById(idGroup);
			if(groupInDb.getId() == -1 || groupInDb.getIdUser() != idUser) 
				return (ResponsePaginationDto<ObjectiveDto>) this.generateResponse.fail(new NotFound("The group does not belong to the user or the group does not exist, the id is " + idGroup));
			
			
			int total = this.objectiveRepository.getTotalByIdGroup(idGroup);
			int limit = request.getLimit() <= 0 ? 10 : request.getLimit();
			int currentPage = request.getCurrentPage() <= 0 ? 1 : request.getCurrentPage();
			int start = (currentPage - 1) * request.getLimit();
			
			var objectives = this.objectiveRepository.getAllByIdGroup(idGroup, idUser, start, limit);
			var objectivesDto = new ArrayList<ObjectiveDto>();
			for(int i = 0; i < objectives.size(); i++) {
				objectivesDto.add(this.objectiveMapper.createDto(objectives.get(i)));
			}
			
			return this.generateResponse.ok(objectivesDto, currentPage, limit, total);
		}catch(SQLException ex) {
			return (ResponsePaginationDto<ObjectiveDto>) this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}
	}
	
	public ResponseDto<Integer> getTotalByIdGroup(RequestDto<Integer> request){
		int total = -1;
		if(request == null)
			return this.generateResponseInt.fail(new InvalidRequest("The request is null!"));
		
		var idGroup = request.body;
		var idUser = request.tokenDetails.getId();
		
		if(idGroup <= 0 || idUser <= 0) 
			return this.generateResponseInt.fail(new InvalidParameter("The id group or id user must be superior than 0!"));
		
		try {
			var groupInDb = this.groupRepository.getById(idGroup);
			if(groupInDb.getId() == -1 || groupInDb.getIdUser() != idUser) 
				return this.generateResponseInt.fail(new NotFound("The group does not belong to the user or the group does not exist, the id is " + idGroup));
			
			total = this.objectiveRepository.getTotalByIdGroup(idGroup);
		}catch(SQLException ex) {
			return this.generateResponseInt.fail(new DatabaseException(ex.getMessage()));
		}
		
		
		var arr = new ArrayList<Integer>();
		arr.add(total);
		return this.generateResponseInt.ok(arr);
	}
	
	public ResponseDto<ObjectiveDto> add(RequestDto<ObjectiveDto> request){
		if(request == null)
			return this.generateResponse.fail(new InvalidRequest("The request is null!"));
		
		var objectiveDto = request.body;
		var userId = request.tokenDetails.getId();

		var objectiveToAdd = this.objectiveMapper.createEntity(objectiveDto);
			
		if(!this.objectiveValidator.isValid(objectiveToAdd)) {
			var messageError = this.objectiveValidator.getMessageError(objectiveToAdd);
			return this.generateResponse.fail(new InvalidParameter(messageError));
		}
		
		Objective objectiveAdded = null;
		try {
			var groupInDb = this.groupRepository.getById(objectiveDto.getIdGroup());
			if(groupInDb.getId() == -1 || groupInDb.getIdUser() != userId)
				return this.generateResponse.fail(
					new Forbidden("The group where you are trying to insert an objective does not exist or does not belong to the user, the id is " + objectiveDto.getIdGroup())
				);
			
			objectiveAdded = this.objectiveRepository.add(objectiveToAdd);
		}catch(SQLException ex) {
			return this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}
		
		var arr = new ArrayList<ObjectiveDto>();
		arr.add(this.objectiveMapper.createDto(objectiveAdded));
		return this.generateResponse.ok(arr);
	}
	
	public ResponseDto<ObjectiveDto> updateProgress(RequestDto<ObjectiveDto> request){
		if(request == null)
			return this.generateResponse.fail(new InvalidRequest("The request is null!"));
		
		var objectiveDto = request.body;
		var userId = request.tokenDetails.getId();
		var objectiveToUpdate = this.objectiveMapper.createEntity(objectiveDto);
		
		if(!this.objectiveValidator.isValid(objectiveToUpdate)) {
			var messageError = this.objectiveValidator.getMessageError(objectiveToUpdate);
			return this.generateResponse.fail(new InvalidParameter(messageError));
		}
		
		try {
			var groupInDb = this.groupRepository.getById(objectiveToUpdate.getIdGroup());
			if(groupInDb.getId() == -1 || groupInDb.getIdUser() != userId) {
				return this.generateResponse.fail(
					new Forbidden("The group where you're trying to update an objective does not exist or does not belong to the user, the id is " + objectiveDto.getIdGroup())
				);
			}
			
			this.objectiveRepository.update(objectiveToUpdate);
		}catch(SQLException ex) {
			return this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}

		var arr = new ArrayList<ObjectiveDto>();
		arr.add(objectiveDto);
		return this.generateResponse.ok(arr);	
	}
	
	public ResponseDto<Integer> delete(RequestDto<Integer> request){
		if(request == null)
			return this.generateResponseInt.fail(new InvalidRequest("The request is null!"));
		
		var id = request.body;
		var userId = request.tokenDetails.getId();
		
		if(userId <= 0)
			return this.generateResponseInt.fail(new InvalidParameter("The user id must be superior than 0"));
		if(id <= 0) 
			return this.generateResponseInt.fail(new InvalidParameter("The id to delete must be superior than 0"));
		
		try {
			if(!this.objectiveRepository.doesObjectiveBelongsToUser(id, userId))
				return this.generateResponseInt.fail(new Forbidden("You can't delete an objective that does not belong to the user"));
			
			this.objectiveRepository.delete(id);
		}catch(SQLException ex) {
			return this.generateResponseInt.fail(new DatabaseException(ex.getMessage()));
		}

		var arr = new ArrayList<Integer>();
		arr.add(id);
		return this.generateResponseInt.ok(arr);
		
	}
	
}
