package com.springboot.app.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.application.dtos.MaterialDto;
import com.springboot.app.application.dtos.RequestDto;
import com.springboot.app.application.dtos.RequestPaginationDto;
import com.springboot.app.application.helpers.JwtHelper;
import com.springboot.app.application.services.AuthenticationService;
import com.springboot.app.application.services.MaterialService;

@RestController
public class MaterialController {
	
	private MaterialService materialService;
	private AuthenticationService authenticationService;
	
	private GeneratorResponseEntity<MaterialDto> generatorResponse;
	private GeneratorResponseEntity<Integer> generatorResponseInt;
	
	
	public MaterialController(MaterialService materialService, AuthenticationService authenticationService) {
		this.materialService = materialService;
		this.authenticationService = authenticationService;
		
		this.generatorResponse = new GeneratorResponseEntity<MaterialDto>();
		this.generatorResponseInt = new GeneratorResponseEntity<Integer>();
	}
	
	@GetMapping(path = "/vicultura/api/v1/materials/{idMaterial}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<MaterialDto>> getById(@PathVariable("idMaterial") String idMaterial){
		var request = new RequestDto<Integer>();
		var responseHttp = new ResponseHttp<MaterialDto>();
		
		request.body = Integer.parseInt(idMaterial);
		var response = this.materialService.getById(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess())
			return this.generatorResponse.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());

		return this.generatorResponse.generate(200, responseHttp);
	}
	
	@GetMapping(path="/vicultura/api/v1/materials/total", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<Integer>> getTotal(){
		var responseHttp = new ResponseHttp<Integer>();
		
		var response = this.materialService.getTotal();
		responseHttp.setResponse(response);
		
		if(!response.isSuccess()) 
			return this.generatorResponseInt.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		return this.generatorResponseInt.generate(200, responseHttp);
	}
	
	@RequestMapping(
		value="/vicultura/api/v1/materials", 
		params = {"currentPage", "limit"}, 
		method = RequestMethod.GET, 
		headers = "Accept=application/json",
		produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<MaterialDto>> getAll(@RequestParam(required = true) String currentPage, @RequestParam(required = true) String limit){
		var request = new RequestPaginationDto<MaterialDto>();
		var responseHttp = new ResponseHttp<MaterialDto>();
		
		request.setCurrentPage(Integer.parseInt(currentPage));
		request.setLimit(Integer.parseInt(limit));
		
		var response = this.materialService.getAll(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess())
			return this.generatorResponse.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		return this.generatorResponse.generate(200, responseHttp);
	}
	
	@RequestMapping(
		value="/vicultura/api/v1/materials", 
		params = { "search" },
		method = RequestMethod.GET,
		headers = "Accept=application/json",
		produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<MaterialDto>> searchNameByRegexp(@RequestParam(required = true) String search){
		var request = new RequestDto<String>();
		var responseHttp = new ResponseHttp<MaterialDto>();
		
		request.body = search;
		
		var response = this.materialService.searchNameByRegexp(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess()) 
			return this.generatorResponse.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());

		return this.generatorResponse.generate(200, responseHttp);
	}
	
	@PostMapping(path="/vicultura/api/v1/materials", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<MaterialDto>> add(@RequestBody MaterialDto materialDto, @CookieValue(name="vicultura_token", required = false) String token){
		var request = new RequestDto<MaterialDto>();
		var responseHttp = new ResponseHttp<MaterialDto>();
		
		if(token == null) 
			return this.generatorResponse.generate(401, responseHttp, "The token is null!");
		
		if(!this.authenticationService.isTokenValid(token))
			return this.generatorResponse.generate(401, responseHttp, "The token is not valid!");		
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		if(userTokenDetails.getId() != 5)
			return this.generatorResponse.generate(403, responseHttp, "You don't have permission to add materials!");
		
		request.body = materialDto;
		request.tokenDetails = userTokenDetails;
		
		var response = this.materialService.add(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess()) 
			return this.generatorResponse.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		return this.generatorResponse.generate(201, responseHttp);
	}
	
	@PutMapping(path="/vicultura/api/v1/materials", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<MaterialDto>> update(@RequestBody MaterialDto materialDto, @CookieValue(name="vicultura_token", required = false) String token){
		var request = new RequestDto<MaterialDto>();
		var responseHttp = new ResponseHttp<MaterialDto>();
		
		if(token == null) 
			return this.generatorResponse.generate(401, responseHttp, "The token is null!");
		
		if(!this.authenticationService.isTokenValid(token))
			return this.generatorResponse.generate(401, responseHttp, "The token is not valid!");		
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		if(userTokenDetails.getId() != 5)
			return this.generatorResponse.generate(403, responseHttp, "You don't have permission to add materials!");
		
		request.body = materialDto;
		request.tokenDetails = userTokenDetails;
		
		var response = this.materialService.update(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess()) 
			return this.generatorResponse.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		return this.generatorResponse.generate(201, responseHttp);
	}
	
	
	
}
