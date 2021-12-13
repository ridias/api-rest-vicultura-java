package com.springboot.app.domain.entities;

import java.time.LocalDateTime;

public class Session extends BaseEntity{
	
	private String token;
	private LocalDateTime dateCreated;
	private LocalDateTime dateExpiration;
	private int idUser;
	
	public Session() {
		super(-1);
		this.idUser = -1;
	}
	
	public Session(int id, String token, LocalDateTime dateCreated, LocalDateTime dateExpiration, int idUser) {
		super(id);
		this.token = token;
		this.dateCreated = dateCreated;
		this.dateExpiration = dateExpiration;
		this.idUser = idUser;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public LocalDateTime getDateExpiration() {
		return dateExpiration;
	}
	
	public void setDateExpiration(LocalDateTime dateExpiration) {
		this.dateExpiration = dateExpiration;
	}
	
	public int getUser() {
		return idUser;
	}
	
	public void setUser(int user) {
		this.idUser = user;
	}
}
