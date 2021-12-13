package com.springboot.app.application.dtos;

import java.util.List;

import com.springboot.app.application.exceptions.CustomException;

public class ResponseDto<T> {
	
	private boolean success;
	private int totalCount;
	private List<T> items;
	private CustomException err;


	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public void setItems(List<T> items) {
		this.items = items;
	}
	
	public void setCustomException(CustomException err) {
		this.err = err;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public List<T> getItems() {
		return items;
	}

	public CustomException getErr() {
		return err;
	}
}
