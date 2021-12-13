package com.springboot.app.domain.entities;

import java.time.LocalDateTime;


public class Group extends BaseEntity{
	
	private String name;
	private String description;
	private LocalDateTime dateCreated;
	private LocalDateTime dateEnd;
	private int idUser;
	
	public Group() {
		super(-1);
		this.idUser = -1;
	}
	
	public Group(int id, String name, String description, LocalDateTime dateCreated, LocalDateTime dateEnd, int idUser) {
		super(id);
		this.name = name;
		this.description = description;
		this.dateCreated = dateCreated;
		this.dateEnd = dateEnd;
		this.idUser = idUser;
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
	
	public int getIdUser() {
		return this.idUser;
	}
	
	public void setUser(int idUser) {
		this.idUser = idUser;
	}

	@Override
	public String toString() {
		return "Group [id=" + this.getId() + ", name=" + name + ", description=" + description + ", user=" + idUser + "]";
	}
	
	
	
}
