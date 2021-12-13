package com.springboot.app.controllers;

import org.springframework.http.ResponseEntity;

import com.springboot.app.application.dtos.ResponseDto;

public class ResponseHttp<T> {
	
	private String status;
	private int statusCode;
	private ResponseDto<T> response;
	
	public void ok(ResponseDto<T> response) {
		this.setBaseResponseHttp("OK", 200, response);
	}
	
	public void created(ResponseDto<T> response) {
		this.setBaseResponseHttp("Created", 201, response);
	}
	
	public void noContent(ResponseDto<T> response) {
		this.setBaseResponseHttp("No Content", 204, response);
	}
	
	public void badRequest(ResponseDto<T> response) {
		this.setBaseResponseHttp("Bad Request", 400, response);
	}
	
	public void unauthorized(ResponseDto<T> response) {
		this.setBaseResponseHttp("Unauthorized", 401, response);
	}
	
	public void forbidden(ResponseDto<T> response) {
		this.setBaseResponseHttp("Forbidden", 403, response);
	}
	
	public void notFound(ResponseDto<T> response) {
		this.setBaseResponseHttp("Not Found", 404, response);
	}
	
	public void serverError(ResponseDto<T> response) {
		this.setBaseResponseHttp("Server Error", 500, response);
	}
	
	public void setBaseResponseHttpByStatusCode(int code, ResponseDto<T> response) {
		if(code == 200) this.ok(response);
		else if(code == 201) this.created(response);
		else if(code == 204) this.noContent(response);
		else if(code == 400) this.badRequest(response);
		else if(code == 401) this.unauthorized(response);
		else if(code == 403) this.forbidden(response);
		else if(code == 404) this.notFound(response);
		else this.serverError(response);
	}
	
	private void setBaseResponseHttp(String status, int statusCode, ResponseDto<T> response) {
		this.status = status;
		this.statusCode = statusCode;
		this.response = response;
	}

	public String getStatus() {
		return status;
	}

	public int getStatusCode() {
		return statusCode;
	}
	
	public ResponseDto<T> getResponse(){
		return response;
	}
}
