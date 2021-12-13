package com.springboot.app.infrastructure.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.springboot.app.application.interfaces.SessionRepository;
import com.springboot.app.domain.entities.Session;
import com.springboot.app.infrastructure.MysqlConnection;

@Repository
public class SessionMysqlRepository implements SessionRepository{

	private Connection conn;
	
	public SessionMysqlRepository() {
		
	}
	
	
	@Override
	public Session getToken(int idUser) throws SQLException {
		var session = new Session();
		this.conn = MysqlConnection.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT * FROM viculturadb.sessions WHERE id_user = " + idUser + " ORDER BY id DESC LIMIT 1";
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) session = this.create(rset);
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return session;
	}

	@Override
	public void add(Session session) throws SQLException {
		
		this.conn = MysqlConnection.getConnection();
		try {
			var query = "INSERT INTO viculturadb.sessions Values(?, ?, ?, ?, ?);";
			PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, session.getToken());
			preparedStmt.setTimestamp(3, Timestamp.valueOf(session.getDateCreated()));
			preparedStmt.setTimestamp(4, Timestamp.valueOf(session.getDateExpiration()));
			preparedStmt.setInt(5, session.getUser());
			preparedStmt.executeUpdate();
			
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
	}
	
	private Session create(ResultSet rset) throws SQLException {
		var session = new Session();
		session.setId(rset.getInt("id"));
		session.setToken(rset.getString("token"));
		session.setDateCreated(rset.getTimestamp("date_created").toLocalDateTime());
		session.setDateExpiration(rset.getTimestamp("date_expiration").toLocalDateTime());
		session.setUser(rset.getInt("id_user"));
		return session;
		
	}

	
	
}
