package com.springboot.app.controllers;

import com.springboot.app.application.dtos.ResponseDto;

public class ResponseHttp<T> {

	private ResponseDto<T> response;
	private String err; 
	
	public void setResponse(ResponseDto<T> response) {
		this.response = response;
	}
	
	public ResponseDto<T> getResponse(){
		return response;
	}
	
	public void setError(String message) {
		this.err = message;
	}
	
	public String getMessageErr() {
		return this.err;
	}
}
