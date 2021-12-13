package com.springboot.app.application.dtos;

public class ObjectiveDto {
	
	private int id;
	private int minProgress;
	private int maxProgress;
	private int currentProgress;
	private String nameMaterial;
	private int yearMaterial;
	private String urlImageMaterial;
	private String urlDetailsMaterial;
	private int idGroup;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMinProgress() {
		return minProgress;
	}
	public void setMinProgress(int minProgress) {
		this.minProgress = minProgress;
	}
	public int getMaxProgress() {
		return maxProgress;
	}
	public void setMaxProgress(int maxProgress) {
		this.maxProgress = maxProgress;
	}
	public int getCurrentProgress() {
		return currentProgress;
	}
	public void setCurrentProgress(int currentProgress) {
		this.currentProgress = currentProgress;
	}
	public String getNameMaterial() {
		return nameMaterial;
	}
	public void setNameMaterial(String nameMaterial) {
		this.nameMaterial = nameMaterial;
	}
	public int getYearMaterial() {
		return yearMaterial;
	}
	public void setYearMaterial(int yearMaterial) {
		this.yearMaterial = yearMaterial;
	}
	public String getUrlImageMaterial() {
		return urlImageMaterial;
	}
	public void setUrlImageMaterial(String urlImageMaterial) {
		this.urlImageMaterial = urlImageMaterial;
	}
	public String getUrlDetailsMaterial() {
		return urlDetailsMaterial;
	}
	public void setUrlDetailsMaterial(String urlDetailsMaterial) {
		this.urlDetailsMaterial = urlDetailsMaterial;
	}
	
	public int getIdGroup() {
		return this.idGroup;
	}
	
	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}
	
}
