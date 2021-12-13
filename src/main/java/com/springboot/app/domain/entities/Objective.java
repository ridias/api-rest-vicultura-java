package com.springboot.app.domain.entities;

import java.time.LocalDateTime;

public class Objective extends BaseEntity {
	
	private int minProgress;
	private int maxProgress;
	private LocalDateTime dateCreated;
	private int idGroup;
	private Material material;
	private int currentProgress;
	
	public Objective() {
		super(-1);
		this.idGroup = -1;
		this.minProgress = -1;
		this.maxProgress = -1;
		this.currentProgress = -1;
		this.material = new Material();
	}
	
	public Objective(int id, int minProgress, int maxProgress, LocalDateTime dateCreated, int idGroup, Material material, int currentProgress) {
		super(id);
		this.minProgress = minProgress;
		this.maxProgress = maxProgress;
		this.dateCreated = dateCreated;
		this.idGroup = idGroup;
		this.material = material;
		this.currentProgress = currentProgress;
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
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
	public int getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public int getCurrentProgress() {
		return currentProgress;
	}
	public void setCurrentProgress(int currentProgress) {
		this.currentProgress = currentProgress;
	}

	@Override
	public String toString() {
		return "Objective [minProgress=" + minProgress + ", maxProgress=" + maxProgress + ", dateCreated=" + dateCreated
				+ ", idGroup=" + idGroup + ", material=" + material + ", currentProgress=" + currentProgress + "]";
	}
	
	
}
