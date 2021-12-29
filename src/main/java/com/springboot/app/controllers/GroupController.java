package com.springboot.app.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.application.dtos.GroupDto;
import com.springboot.app.application.dtos.ObjectiveDto;
import com.springboot.app.application.dtos.RequestDto;
import com.springboot.app.application.dtos.RequestPaginationDto;
import com.springboot.app.application.helpers.JwtHelper;
import com.springboot.app.application.services.AuthenticationService;
import com.springboot.app.application.services.GroupService;
import com.springboot.app.application.services.ObjectiveService;

@RestController
public class GroupController {

	private GroupService groupService;
	private AuthenticationService authenticationService;
	private ObjectiveService objectiveService;
	
	private GeneratorResponseEntity<GroupDto> generatorResponse;
	private GeneratorResponseEntity<Integer> generatorResponseInt;
	private GeneratorResponseEntity<ObjectiveDto> generatorResponseObjective;
	
	public GroupController(GroupService service, ObjectiveService objectiveService, AuthenticationService authenticationService) {
		this.groupService = service;
		this.objectiveService = objectiveService;
		this.authenticationService = authenticationService;
		
		this.generatorResponse = new GeneratorResponseEntity<GroupDto>();
		this.generatorResponseInt = new GeneratorResponseEntity<Integer>();
		this.generatorResponseObjective = new GeneratorResponseEntity<ObjectiveDto>();
	}
	
	@GetMapping(path = "/vicultura/v1/api/groups")
	@ResponseBody
	public ResponseEntity<ResponseHttp<GroupDto>> getAllByIdUser(@CookieValue(name="vicultura_token", required = false) String token){
		var request = new RequestDto<GroupDto>();
		var responseHttp = new ResponseHttp<GroupDto>();
		
		if(token == null)
			return this.generatorResponse.generate(401, responseHttp, "The token is null");
		
		if(!this.authenticationService.isTokenValid(token))
			return this.generatorResponse.generate(401, responseHttp, "The token is not valid!");
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		request.tokenDetails = userTokenDetails;
		
		var response = this.groupService.getAllByUserId(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess())
			return this.generatorResponse.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		
		return this.generatorResponse.generate(200, responseHttp);
	}
	
	@GetMapping(path = "/vicultura/v1/api/groups/{idGroup}")
	@ResponseBody
	public ResponseEntity<ResponseHttp<ObjectiveDto>> getAllObjectivesByIdGroup(
			@PathVariable("idGroup") String idGroup, 
			@RequestParam(required = true) String currentPage, 
			@RequestParam(required = true) String limit, 
			@CookieValue(name="vicultura_token", required = false) String token){
		
		var request = new RequestPaginationDto<Integer>();
		var responseHttp = new ResponseHttp<ObjectiveDto>();
		
		if(token == null)
			return this.generatorResponseObjective.generate(401, responseHttp, "The token is null");
		
		if(!this.authenticationService.isTokenValid(token))
			return this.generatorResponseObjective.generate(401, responseHttp, "The token is not valid!");
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		
		request.setCurrentPage(Integer.parseInt(currentPage));
		request.setLimit(Integer.parseInt(limit));
		request.tokenDetails = userTokenDetails;
		
		var response = this.objectiveService.getAllByIdGroup(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess())
			return this.generatorResponseObjective.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		return this.generatorResponseObjective.generate(200, responseHttp);
	}
	
	@GetMapping(path = "/vicultura/v1/api/groups/{idGroup}/total")
	@ResponseBody
	public ResponseEntity<ResponseHttp<Integer>> getTotalByGroupId(@PathVariable("idGroup") String idGroup, @CookieValue(name="vicultura_token", required = false) String token){
		var request = new RequestDto<Integer>();
		var responseHttp = new ResponseHttp<Integer>();
		
		if(token == null)
			return this.generatorResponseInt.generate(401, responseHttp, "The token is null");
		
		if(!this.authenticationService.isTokenValid(token))
			return this.generatorResponseInt.generate(401, responseHttp, "The token is not valid!");
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		request.body = Integer.parseInt(idGroup);
		request.tokenDetails = userTokenDetails;
		
		var response = this.objectiveService.getTotalByIdGroup(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess())
			return this.generatorResponseInt.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		
		return this.generatorResponseInt.generate(200, responseHttp);
	}
	
	@PostMapping(path = "/vicultura/api/v1/groups", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<GroupDto>> add(@RequestBody GroupDto groupDto, @CookieValue(name="vicultura_token", required = false) String token) {
		var request = new RequestDto<GroupDto>();
		var responseHttp = new ResponseHttp<GroupDto>();
		
		if(token == null)
			return this.generatorResponse.generate(401, responseHttp, "The token is null");
		
		if(!this.authenticationService.isTokenValid(token))
			return this.generatorResponse.generate(401, responseHttp, "The token is not valid!");
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		request.body = groupDto;
		request.tokenDetails = userTokenDetails;
		
		var response = this.groupService.add(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess())
			return this.generatorResponse.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		
		return this.generatorResponse.generate(201, responseHttp);
	}
	
	@PutMapping(path = "/vicultura/api/v1/groups", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<GroupDto>> update(@RequestBody GroupDto groupDto, @CookieValue(name="vicultura_token", required = false) String token) {
		var request = new RequestDto<GroupDto>();
		var responseHttp = new ResponseHttp<GroupDto>();
		
		if(token == null)
			return this.generatorResponse.generate(401, responseHttp, "The token is null");
		
		if(!this.authenticationService.isTokenValid(token))
			return this.generatorResponse.generate(401, responseHttp, "The token is not valid!");
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		request.body = groupDto;
		request.tokenDetails = userTokenDetails;
		
		var response = this.groupService.update(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess())
			return this.generatorResponse.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		
		return this.generatorResponse.generate(200, responseHttp);
	}
	
	@DeleteMapping(path = "/vicultura/api/v1/groups/{idGroup}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<Integer>> delete(@PathVariable String idGroup, @CookieValue(name="vicultura_token", required = false) String token) {
		var request = new RequestDto<Integer>();
		var responseHttp = new ResponseHttp<Integer>();
		
		if(token == null)
			return this.generatorResponseInt.generate(401, responseHttp, "The token is null");
		
		if(!this.authenticationService.isTokenValid(token))
			return this.generatorResponseInt.generate(401, responseHttp, "The token is not valid!");
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		request.body = Integer.parseInt(idGroup);
		request.tokenDetails = userTokenDetails;
		
		var response = this.groupService.delete(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess())
			return this.generatorResponseInt.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		
		return this.generatorResponseInt.generate(200, responseHttp);
	}
	
}
