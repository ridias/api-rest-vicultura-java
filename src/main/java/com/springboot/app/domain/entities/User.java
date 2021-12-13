package com.springboot.app.domain.entities;

import java.time.LocalDateTime;

public class User extends BaseEntity {
	
	private String username;
	private String email;
	private String password;
	private LocalDateTime dateCreated;
	
	public User() {
		super(-1);
	}
	
	public User(int id, String username, String email, String password, LocalDateTime dateCreated) {
		super(id);
		this.username = username;
		this.email = email;
		this.password = password;
		this.dateCreated = dateCreated;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	@Override
	public String toString() {
		return "User [id=" + this.getId() + ", username=" + username + ", email=" + email + "]";
	}
}
