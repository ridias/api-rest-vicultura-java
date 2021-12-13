package com.springboot.app.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.application.dtos.MaterialDto;
import com.springboot.app.application.dtos.RequestDto;
import com.springboot.app.application.services.MaterialService;

@RestController
public class MaterialController {
	
	private MaterialService materialService;
	
	private GeneratorResponseEntity<ResponseHttp<MaterialDto>> generatorResponse;
	private GeneratorResponseEntity<ResponseHttp<Integer>> generatorResponseInt;
	
	
	public MaterialController(MaterialService materialService) {
		this.materialService = materialService;
		this.generatorResponse = new GeneratorResponseEntity<ResponseHttp<MaterialDto>>();
		this.generatorResponseInt = new GeneratorResponseEntity<ResponseHttp<Integer>>();
	}
	
	@GetMapping(path = "/vicultura/api/v1/materials/{idMaterial}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<MaterialDto>> getById(@PathVariable("idMaterial") int idMaterial){
		var request = new RequestDto<Integer>();
		var responseHttp = new ResponseHttp<MaterialDto>();
		
		request.body = idMaterial;
		var response = this.materialService.getById(request);
		
		if(!response.isSuccess()) {
			responseHttp.setBaseResponseHttpByStatusCode(response.getErr().getCode(), response);
			return this.generatorResponse.generate(response.getErr().getCode(), responseHttp);
		}else {
			responseHttp.setBaseResponseHttpByStatusCode(200, response);
			return this.generatorResponse.generate(200, responseHttp);
		}
	}
	
	@GetMapping(path="/vicultura/api/v1/materials/total", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<Integer>> getTotal(){
		var request = new RequestDto<MaterialDto>();
		var responseHttp = new ResponseHttp<Integer>();
		
		var response = this.materialService.getTotal();
		
		if(!response.isSuccess()) {
			responseHttp.setBaseResponseHttpByStatusCode(response.getErr().getCode(), response);
			return this.generatorResponseInt.generate(0, null)
		}else {
			responseHttp.setBaseResponseHttpByStatusCode(200, response);
		}
		
		return new ResponseEntity<ResponseHttp<Integer>>(responseHttp, HttpStatus.OK);
	}
	
}
