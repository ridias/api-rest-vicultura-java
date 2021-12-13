package com.springboot.app.application.interfaces;

import java.sql.SQLException;

public interface Delete {
	
	public void delete(int id) throws SQLException;
}
