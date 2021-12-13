package com.springboot.app.application.dtos;

import java.time.LocalDateTime;

public class MaterialDto {
	
	private int id;
	private String name;
	private int year;
	private LocalDateTime dateCreated;
	private String urlImage;
	private String urlDetails;
	
	public MaterialDto() {
		
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getUrlDetails() {
		return urlDetails;
	}

	public void setUrlDetails(String urlDetails) {
		this.urlDetails = urlDetails;
	}
}
