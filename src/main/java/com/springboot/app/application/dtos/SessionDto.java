package com.springboot.app.application.dtos;

import java.time.LocalDateTime;

public class SessionDto {
	
	private String token;
	private LocalDateTime dateExpiration;
	private int idUser;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public LocalDateTime getDateExpiration() {
		return dateExpiration;
	}
	public void setDateExpiration(LocalDateTime dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
}
