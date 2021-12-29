package com.springboot.app.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.application.dtos.RequestDto;
import com.springboot.app.application.dtos.SessionDto;
import com.springboot.app.application.dtos.UserLoginDto;
import com.springboot.app.application.dtos.UserRegisterDto;
import com.springboot.app.application.services.AuthenticationService;

@RestController
public class AuthenticationController {
	
	private AuthenticationService authenticationService;
	private GeneratorResponseEntity<SessionDto> generatorResponseSession;
	
	public AuthenticationController(AuthenticationService authentication) {
		this.authenticationService = authentication;
		this.generatorResponseSession = new GeneratorResponseEntity<SessionDto>();
	}
	
	@PostMapping(path = "/vicultura/api/v1/register", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<SessionDto>> register(@RequestBody UserRegisterDto user) {
		var request = new RequestDto<UserRegisterDto>();
		var responseHttp = new ResponseHttp<SessionDto>();
		
		request.body = user;
		
		var response = this.authenticationService.register(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess())
			return this.generatorResponseSession.generate(response.getErr().getCode(), responseHttp);
		
		return this.generatorResponseSession.generate(201, responseHttp);
	}
	
	/*@PostMapping(path = "/vicultura/api/v1/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> logout(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", "vicultura_token=deleted; Path=/vicultura/v1/api/; Max-Age=0; expires=Thu, 01 Jan 1970 00:00:00 GMT");
		return new ResponseEntity<>(headers, HttpStatus.OK);
	}*/
	
	@PostMapping(path = "/vicultura/api/v1/login", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ResponseHttp<SessionDto>> login(@RequestBody UserLoginDto user){
		
		var request = new RequestDto<UserLoginDto>();
		var responseHttp = new ResponseHttp<SessionDto>();
		
		request.body = user;
		
		var response = this.authenticationService.login(request);
		responseHttp.setResponse(response);
		
		if(!response.isSuccess())
			return this.generatorResponseSession.generate(response.getErr().getCode(), responseHttp, response.getErr().getMessage());
		
		String access_token = response.getItems().get(0).getToken();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", "vicultura_token=" + access_token +"; Max-Age=604800; Path=/vicultura/v1/api/; Secure; HttpOnly");
		return this.generatorResponseSession.generate(200, responseHttp);
	}
}
