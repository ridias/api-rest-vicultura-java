package com.springboot.app.domain.entities;

public abstract class BaseEntity {
	
	private int id;
	
	public BaseEntity(int id) {
		this.id = id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
}
