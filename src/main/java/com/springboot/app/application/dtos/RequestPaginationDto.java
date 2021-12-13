package com.springboot.app.application.dtos;

public class RequestPaginationDto<T> extends RequestDto<T>{

	private int currentPage;
	private int limit;
	
	public RequestPaginationDto() {
		super();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
