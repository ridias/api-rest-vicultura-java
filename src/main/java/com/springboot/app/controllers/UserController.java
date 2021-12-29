package com.springboot.app.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.application.dtos.RequestDto;
import com.springboot.app.application.dtos.UserUpdateDto;
import com.springboot.app.application.dtos.UserUpdatePasswordDto;
import com.springboot.app.application.helpers.JwtHelper;
import com.springboot.app.application.services.AuthenticationService;
import com.springboot.app.application.services.UserService;

@RestController
public class UserController {

	private UserService userService;
	private AuthenticationService authenticationService;
	
	private GeneratorResponseEntity<UserUpdateDto> generatorResponse;
	private GeneratorResponseEntity<String> generatorResponseUpdatePassword;
	
	
	public UserController(UserService service, AuthenticationService authenticationService) {
		this.userService = service;
		this.authenticationService = authenticationService;
		
		this.generatorResponse = new GeneratorResponseEntity<UserUpdateDto>();
		this.generatorResponseUpdatePassword = new GeneratorResponseEntity<String>();
	}
	
	@PutMapping(path = "/vicultura/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<UserUpdateDto>> update(@RequestBody UserUpdateDto userUpdateDto, @CookieValue(name="vicultura_token", required = false) String token) {
		var request = new RequestDto<UserUpdateDto>();
		var responseHttp = new ResponseHttp<UserUpdateDto>();
		
		if(token == null)
			return this.generatorResponse.generate(401, responseHttp, "The token is null");
		
		if(!this.authenticationService.isTokenValid(token))
			return this.generatorResponse.generate(401, responseHttp, "The token is not valid!");
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		
		request.body = userUpdateDto;
		request.tokenDetails = userTokenDetails;
		
		var response = this.userService.update(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess())
			return this.generatorResponse.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		return this.generatorResponse.generate(200, responseHttp);
	}
	
	@PutMapping(path = "/vicultura/api/v1/users/password", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<String>> updatePassword(@RequestBody UserUpdatePasswordDto userUpdatePasswordDto, @CookieValue(name="vicultura_token", required = false) String token) {
		var request = new RequestDto<UserUpdatePasswordDto>();
		var responseHttp = new ResponseHttp<String>();
		
		if(token == null)
			return this.generatorResponseUpdatePassword.generate(401, responseHttp, "The token is null");
		
		if(!this.authenticationService.isTokenValid(token))
			return this.generatorResponseUpdatePassword.generate(401, responseHttp, "The token is not valid!");
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		
		request.body = userUpdatePasswordDto;
		request.tokenDetails = userTokenDetails;
		
		var response = this.userService.updatePassword(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess())
			return this.generatorResponseUpdatePassword.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		return this.generatorResponseUpdatePassword.generate(200, responseHttp);
	}
}
