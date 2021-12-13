package com.springboot.app.controllers;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.application.dtos.GroupDto;
import com.springboot.app.application.dtos.RequestDto;
import com.springboot.app.application.dtos.ResponseDto;
import com.springboot.app.application.exceptions.CustomException;
import com.springboot.app.application.exceptions.InvalidToken;
import com.springboot.app.application.helpers.JwtHelper;
import com.springboot.app.application.services.GroupService;

@RestController
public class GroupController {

	private GroupService groupService;
	
	public GroupController(GroupService service) {
		this.groupService = service;
	}
	
	@GetMapping(path = "/vicultura/v1/api/groups")
	@ResponseBody
	public ResponseHttp<GroupDto> getAllByIdUser(@CookieValue(name="vicultura_token", required = false) String token){
		var response = new ResponseDto<GroupDto>();
		var responseHttp = new ResponseHttp<GroupDto>();
		
		if(token == null) {
			
			return responseHttp;
		}
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		userTokenDetails.setToken(token);
		var request = new RequestDto<String>();
		request.body = null;
		request.tokenDetails = userTokenDetails;
		
		response = this.groupService.getAllByUserId(null);
		if(!response.isSuccess()) {
			
		}
		
		responseHttp.ok(response);
		return responseHttp;
	}
	
	@PostMapping(path = "/vicultura/v1/api/{idUser}/groups")
	public void add( @PathVariable("idUser") Integer idUser, @RequestBody GroupDto groupDto) {
		return ;
	}
}
