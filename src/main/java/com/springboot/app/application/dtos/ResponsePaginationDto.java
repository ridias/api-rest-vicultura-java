package com.springboot.app.application.dtos;

public class ResponsePaginationDto<T> extends ResponseDto<T>{

	private int currentPage;
	private int totalPages;
	private int pageSize;
	private boolean hasPreviousPage;
	private boolean hasNextPage;
	private int nextPageNumber;
	private int previousPageNumber;
	
	public ResponsePaginationDto() {
		super();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int getNextPageNumber() {
		return nextPageNumber;
	}

	public void setNextPageNumber(int nextPageNumber) {
		this.nextPageNumber = nextPageNumber;
	}

	public int getPreviousPageNumber() {
		return previousPageNumber;
	}

	public void setPreviousPageNumber(int previousPageNumber) {
		this.previousPageNumber = previousPageNumber;
	}
}
