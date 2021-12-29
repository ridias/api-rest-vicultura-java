package com.springboot.app.application.services;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.app.application.dtos.MaterialDto;
import com.springboot.app.application.dtos.RequestDto;
import com.springboot.app.application.dtos.RequestPaginationDto;
import com.springboot.app.application.dtos.ResponseDto;
import com.springboot.app.application.dtos.ResponsePaginationDto;
import com.springboot.app.application.exceptions.DatabaseException;
import com.springboot.app.application.exceptions.InvalidParameter;
import com.springboot.app.application.exceptions.InvalidRequest;
import com.springboot.app.application.exceptions.NotFound;
import com.springboot.app.application.helpers.GeneratorResponseHelper;
import com.springboot.app.application.interfaces.MaterialRepository;
import com.springboot.app.application.mappers.MaterialMapper;
import com.springboot.app.domain.entities.Material;
import com.springboot.app.domain.validators.MaterialValidator;

@Service
public class MaterialService {
	
	@Autowired
	private MaterialRepository materialRepository;
	@Autowired
	private MaterialValidator materialValidator;
	
	private MaterialMapper materialMapper;
	
	private GeneratorResponseHelper<MaterialDto> generateResponse;
	private GeneratorResponseHelper<Integer> generateResponseInt;
	
	public MaterialService(MaterialRepository materialRepository, MaterialValidator materialValidator) {
		this.materialRepository = materialRepository;
		this.materialValidator = materialValidator;
		
		this.materialMapper = new MaterialMapper();
		this.generateResponse = new GeneratorResponseHelper<MaterialDto>();
		this.generateResponseInt = new GeneratorResponseHelper<Integer>();
	}
	
	public ResponsePaginationDto<MaterialDto> getAll(RequestPaginationDto<MaterialDto> request){
		if(request == null)
			return (ResponsePaginationDto<MaterialDto>) this.generateResponse.fail(new InvalidRequest());
		
		try {
			int total = this.materialRepository.getTotal();
			int limit = request.getLimit() <= 0 ? 10 : request.getLimit();
			int currentPage = request.getCurrentPage() <= 0 ? 1 : request.getCurrentPage();
			int start = (currentPage - 1) * request.getLimit();
			
			var materials = this.materialRepository.getAll(start, limit);
			var materialsDto = new ArrayList<MaterialDto>();
			for(int i = 0; i < materials.size(); i++) {
				materialsDto.add(this.materialMapper.createDto(materials.get(i)));
			}

			return this.generateResponse.ok(materialsDto, currentPage, limit, total);
		}catch(SQLException ex) {
			return (ResponsePaginationDto<MaterialDto>) this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}
	}
	
	public ResponseDto<MaterialDto> getById(RequestDto<Integer> request){
		if(request == null)
			return this.generateResponse.fail(new InvalidRequest());
		
		int idMaterial = request.body;
		if(idMaterial <= 0)
			return this.generateResponse.fail(new InvalidParameter("The id must be superior than 0!"));
		
		Material material = null;
		try {
			material = this.materialRepository.getById(idMaterial);
			if(material.getId() <= 0)
				return this.generateResponse.fail(new NotFound());
			
			var arr = new ArrayList<MaterialDto>();
			arr.add(materialMapper.createDto(material));
			return this.generateResponse.ok(arr);
		}catch(SQLException ex) {
			return this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}
	}
	
	public ResponseDto<MaterialDto> searchNameByRegexp(RequestDto<String> request){
		if(request == null)
			return this.generateResponse.fail(new InvalidRequest());
		
		String name = request.body;
		
		try {
			var materials = new ArrayList<Material>();
			var materialsDto = new ArrayList<MaterialDto>();
			
			materials = (ArrayList<Material>) this.materialRepository.searchNameByRegexp(name, 5);
			for(int i = 0; i < materials.size(); i++) {
				materialsDto.add(this.materialMapper.createDto(materials.get(i)));
			}
			
			return this.generateResponse.ok(materialsDto);
		}catch(SQLException ex) {
			return this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}
	}
	
	public ResponseDto<Integer> getTotal(){
		int total = -1;
		
		try {
			total = this.materialRepository.getTotal();
		}catch(SQLException ex) {
			return this.generateResponseInt.fail(new DatabaseException(ex.getMessage()));
		}
		
		var arr = new ArrayList<Integer>();
		arr.add(total);
		return this.generateResponseInt.ok(arr);
	}
	
	public ResponseDto<MaterialDto> add(RequestDto<MaterialDto> request){
		if(request == null)
			return this.generateResponse.fail(new InvalidRequest());
		
		var materialDto = request.body;
		var materialToAdd = this.materialMapper.createEntity(materialDto);
		
		if(!this.materialValidator.isValidWithoutCheckingId(materialToAdd)) {
			var messageError = this.materialValidator.getMessageError(materialToAdd);
			return this.generateResponse.fail(new InvalidParameter(messageError));
		}
		
		Material materialAdded = null;
		try {
			materialAdded = this.materialRepository.add(materialToAdd);
		}catch(SQLException ex) {
			return this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}

		var arr = new ArrayList<MaterialDto>();
		arr.add(this.materialMapper.createDto(materialAdded));
		return this.generateResponse.ok(arr);
	}
	
	public ResponseDto<MaterialDto> update(RequestDto<MaterialDto> request){
		if(request == null)
			return this.generateResponse.fail(new InvalidRequest());
		
		var materialDto = request.body;
		var materialToUpdate = this.materialMapper.createEntity(materialDto);
		
		if(!this.materialValidator.isValid(materialToUpdate)) {
			var messageError = this.materialValidator.getMessageError(materialToUpdate);
			return this.generateResponse.fail(new InvalidParameter(messageError));
		}
		
		try {
			var materialToUpdateInDb = this.materialRepository.getById(materialToUpdate.getId());
			if(materialToUpdateInDb.getId() == -1)
				return this.generateResponse.fail(new NotFound("The material with id " + materialToUpdate.getId() + " does not exist!"));
			
			this.materialRepository.update(materialToUpdate);
		}catch(SQLException ex) {
			return this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}
		
		var arr = new ArrayList<MaterialDto>();
		arr.add(materialDto);
		return this.generateResponse.ok(arr);
	}
}
