package com.springboot.app.application.helpers;

import java.util.ArrayList;
import java.util.List;

import com.springboot.app.application.dtos.ResponseDto;
import com.springboot.app.application.dtos.ResponsePaginationDto;
import com.springboot.app.application.exceptions.CustomException;

public class GeneratorResponseHelper<T>{

	
	public ResponseDto<T> ok(List<T> result) {
		var response = new ResponseDto<T>();
		response.setSuccess(true);
		response.setTotalCount(result.size());
		response.setItems(result);
		response.setCustomException(null);
		return response;
	}
	
	public ResponsePaginationDto<T> ok(List<T> result, int currentPage, int limit, int total){
		var response = new ResponsePaginationDto<T>();
		boolean hasNextPage = (currentPage + 1) * limit <= total ? true : false ;
		boolean hasPreviousPage = currentPage - 1 < 0 ? false : true;
		int nextPage = (currentPage + 1) * limit <= total ? currentPage + 1 : -1;
		int previousPage = currentPage - 1 < 0 ? -1 : currentPage - 1;
		
		response.setSuccess(true);
		response.setTotalCount(result.size());
		response.setItems(result);
		response.setCustomException(null);
		response.setCurrentPage(currentPage);
		response.setPageSize(limit);
		response.setTotalPages((int)Math.floor(total / limit));
		response.setHasNextPage(hasNextPage);
		response.setHasPreviousPage(hasPreviousPage);
		response.setNextPageNumber(nextPage);
		response.setPreviousPageNumber(previousPage);
		return response;
	}

	public ResponseDto<T> fail(CustomException ex) {
		var response = new ResponseDto<T>();
		response.setSuccess(false);
		response.setTotalCount(0);
		response.setItems(new ArrayList<T>());
		response.setCustomException(ex);
		return response;
	}
	

}
