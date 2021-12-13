package com.springboot.app.application.interfaces;

import java.sql.SQLException;

import com.springboot.app.domain.entities.Session;

public interface SessionRepository {

	public Session getToken(int idUser) throws SQLException;
	public void add(Session session) throws SQLException;
}
