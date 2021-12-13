package com.springboot.app.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	public AuthenticationController(AuthenticationService authentication) {
		this.authenticationService = authentication;
	}
	
	/*@PostMapping(path = "/vicultura/v1/api/register", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponseHttp<SessionDto> register(@RequestBody UserRegisterDto user) {
		var request = new RequestDto<UserRegisterDto>();
		request.body = user;
		
		var response = this.authenticationService.register(request);
		var baseResponseHttp = new BaseResponseHttp<SessionDto>();
		
		if(response.isSuccess()) {
			baseResponseHttp.ok(response);
		}else {
			baseResponseHttp.badRequest(response);
		}
		
		return baseResponseHttp;
	}
	
	@PostMapping(path = "/vicultura/v1/api/logout", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> logout(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", "vicultura_token=deleted; Path=/vicultura/v1/api/; Max-Age=0; expires=Thu, 01 Jan 1970 00:00:00 GMT");
		return new ResponseEntity<>(headers, HttpStatus.OK);
	}
	
	@PostMapping(path = "/vicultura/v1/api/login", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<BaseResponseHttp<SessionDto>> login(@RequestBody UserLoginDto user){
		
		var request = new RequestDto<UserLoginDto>();
		request.body = user;
		
		var response = this.authenticationService.login(request);
		var baseResponseHttp = new BaseResponseHttp<SessionDto>();
		
		if(response.isSuccess()) {
			String access_token = response.getItems().get(0).getToken();
			baseResponseHttp.ok(response);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Set-Cookie", "vicultura_token=" + access_token +"; Max-Age=604800; Path=/vicultura/v1/api/; Secure; HttpOnly");
			return new ResponseEntity<BaseResponseHttp<SessionDto>>(baseResponseHttp, headers, HttpStatus.OK);
		}else {
			baseResponseHttp.unauthorized(response);
			return new ResponseEntity<BaseResponseHttp<SessionDto>>(baseResponseHttp, HttpStatus.UNAUTHORIZED);
		}
	}*/
}
