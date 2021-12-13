package com.springboot.app.application.interfaces;

import java.sql.SQLException;

public interface AddUpdate<T> {
	
	public T add(T entity) throws SQLException;
	public void update(T entity) throws SQLException;
	
}
