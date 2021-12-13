package com.springboot.app.application.dtos;

import java.time.LocalDateTime;

public class GroupDto {
	private int id;
	private String name;
	private String description;
	private LocalDateTime dateCreated;
	private LocalDateTime dateEnd;
	
	public GroupDto() {
		this.id = -1;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public LocalDateTime getDateCreated() {
		return this.dateCreated;
	}
	
	public void setDateCreated(LocalDateTime date) {
		this.dateCreated = date;
	}
	
	public LocalDateTime getDateEnd() {
		return this.dateEnd;
	}
	
	public void setDateEnd(LocalDateTime date) {
		this.dateEnd = date;
	}
}
