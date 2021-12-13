package com.springboot.app.domain.entities;

import java.time.LocalDateTime;

public class Material extends BaseEntity {
	
	private String name;
	private int year;
	private LocalDateTime dateCreated;
	private String urlImage;
	private String urlDetails;
	
	public Material() {
		super(-1);
		this.year = -1;
	}
	
	public Material(int id, String name, int year, LocalDateTime dateCreated, String urlImage, String urlDetails) {
		super(id);
		this.name = name;
		this.year = year;
		this.dateCreated = dateCreated;
		this.urlImage = urlImage;
		this.urlDetails = urlDetails;
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
	
	public String toString() {
		return "name: " + this.name + " - year: " + this.year + " - dateCreated: " +
				this.dateCreated + " - urlImage: " + this.urlImage + " - urlDetails: " +
				this.urlDetails;
	}
}
