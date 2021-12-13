package com.springboot.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GeneratorResponseEntity<T> {
	
	public ResponseEntity<T> generate(int code, T obj) {
		
		if(code == 200) return new ResponseEntity<T>(obj, HttpStatus.OK);
		if(code == 201) return new ResponseEntity<T>(obj, HttpStatus.CREATED);
		if(code == 204) return new ResponseEntity<T>(obj, HttpStatus.CREATED);
		if(code == 400) return new ResponseEntity<T>(obj, HttpStatus.BAD_REQUEST);
		if(code == 401) return new ResponseEntity<T>(obj, HttpStatus.UNAUTHORIZED);
		if(code == 403) return new ResponseEntity<T>(obj, HttpStatus.FORBIDDEN);
		if(code == 404) return new ResponseEntity<T>(obj, HttpStatus.NOT_FOUND);
		return new ResponseEntity<T>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
