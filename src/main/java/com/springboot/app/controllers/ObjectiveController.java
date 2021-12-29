package com.springboot.app.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.application.dtos.ObjectiveDto;
import com.springboot.app.application.dtos.RequestDto;
import com.springboot.app.application.helpers.JwtHelper;
import com.springboot.app.application.services.AuthenticationService;
import com.springboot.app.application.services.ObjectiveService;

@RestController
public class ObjectiveController {

	private ObjectiveService objectiveService;
	private AuthenticationService authenticationService;
	
	private GeneratorResponseEntity<ObjectiveDto> generatorResponseObjective;
	private GeneratorResponseEntity<Integer> generatorResponseInteger;
	
	public ObjectiveController(ObjectiveService service, AuthenticationService authenticationService) {
		this.objectiveService = service;
		this.authenticationService = authenticationService;
		
		this.generatorResponseObjective = new GeneratorResponseEntity<ObjectiveDto>();
		this.generatorResponseInteger = new GeneratorResponseEntity<Integer>();
	}

	@PostMapping(path = "/vicultura/api/v1/objectives", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<ObjectiveDto>> add(@RequestBody ObjectiveDto objectiveDto,
			@CookieValue(name = "vicultura_token", required = false) String token) {
		var request = new RequestDto<ObjectiveDto>();
		var responseHttp = new ResponseHttp<ObjectiveDto>();
		
		if(token == null) 
			return this.generatorResponseObjective.generate(401, responseHttp, "The token is null!");
		
		if(!this.authenticationService.isTokenValid(token))
			return this.generatorResponseObjective.generate(401, responseHttp, "The token is not valid!");		
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		request.body = objectiveDto;
		request.tokenDetails = userTokenDetails;
		
		var response = this.objectiveService.add(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess()) 
			return this.generatorResponseObjective.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		return this.generatorResponseObjective.generate(201, responseHttp);
	}

	@PutMapping(path = "/vicultura/api/v1/objectives", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<ObjectiveDto>> update(@RequestBody ObjectiveDto objectiveDto,
			@CookieValue(name = "vicultura_token", required = false) String token) {
		var request = new RequestDto<ObjectiveDto>();
		var responseHttp = new ResponseHttp<ObjectiveDto>();
		
		if(token == null) 
			return this.generatorResponseObjective.generate(401, responseHttp, "The token is null!");
		
		if(!this.authenticationService.isTokenValid(token))
			return this.generatorResponseObjective.generate(401, responseHttp, "The token is not valid!");		
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		request.body = objectiveDto;
		request.tokenDetails = userTokenDetails;
		
		var response = this.objectiveService.updateProgress(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess()) 
			return this.generatorResponseObjective.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		return this.generatorResponseObjective.generate(200, responseHttp);
	}

	@DeleteMapping(path = "/vicultura/api/v1/objectives/{idObjective}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<Integer>> delete(@PathVariable String idObjective,
			@CookieValue(name = "vicultura_token", required = false) String token) {
		var request = new RequestDto<Integer>();
		var responseHttp = new ResponseHttp<Integer>();
		
		if(token == null) 
			return this.generatorResponseInteger.generate(401, responseHttp, "The token is null!");
		
		if(!this.authenticationService.isTokenValid(token))
			return this.generatorResponseInteger.generate(401, responseHttp, "The token is not valid!");		
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		if(userTokenDetails.getId() != 5)
			return this.generatorResponseInteger.generate(403, responseHttp, "You don't have permission to add materials!");
		
		request.body = Integer.parseInt(idObjective);
		request.tokenDetails = userTokenDetails;
		
		var response = this.objectiveService.delete(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess()) 
			return this.generatorResponseInteger.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		return this.generatorResponseInteger.generate(200, responseHttp);
	}
}
