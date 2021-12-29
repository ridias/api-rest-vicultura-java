package com.springboot.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GeneratorResponseEntity<T> {
	
	public ResponseEntity<ResponseHttp<T>> generate(int code, ResponseHttp<T> obj) {
		if(code == 200) return new ResponseEntity<ResponseHttp<T>>(obj, HttpStatus.OK);
		if(code == 201) return new ResponseEntity<ResponseHttp<T>>(obj, HttpStatus.CREATED);
		if(code == 204) return new ResponseEntity<ResponseHttp<T>>(obj, HttpStatus.CREATED);
		if(code == 400) return new ResponseEntity<ResponseHttp<T>>(obj, HttpStatus.BAD_REQUEST);
		if(code == 401) return new ResponseEntity<ResponseHttp<T>>(obj, HttpStatus.UNAUTHORIZED);
		if(code == 403) return new ResponseEntity<ResponseHttp<T>>(obj, HttpStatus.FORBIDDEN);
		if(code == 404) return new ResponseEntity<ResponseHttp<T>>(obj, HttpStatus.NOT_FOUND);
		return new ResponseEntity<ResponseHttp<T>>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<ResponseHttp<T>> generate(int code, ResponseHttp<T> obj, String messageError) {	
		obj.setError(messageError);
		return this.generate(code, obj);
	}

}
