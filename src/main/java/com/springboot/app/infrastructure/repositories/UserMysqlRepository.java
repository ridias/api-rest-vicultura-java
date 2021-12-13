package com.springboot.app.infrastructure.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import com.springboot.app.application.interfaces.UserRepository;
import com.springboot.app.domain.entities.User;
import com.springboot.app.infrastructure.MysqlConnection;

@Repository
public class UserMysqlRepository implements UserRepository{

	private Connection conn;
	
	public UserMysqlRepository() {
		
	}
	
	@Override
	public User getById(int id) throws SQLException {
		var user = new User();
		this.conn = MysqlConnection.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT * FROM viculturadb.users WHERE id = " + id;
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) user = this.create(rset);
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return user;
	}
	
	@Override
	public User getByUsername(String username) throws SQLException {
		var user = new User();
		this.conn = MysqlConnection.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT * FROM viculturadb.users WHERE username = '" + username + "'";
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) user = this.create(rset);
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return user;
	}
	
	@Override
	public User getByUsernameAndId(int id, String username) throws SQLException {
		var user = new User();
		this.conn = MysqlConnection.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT * FROM viculturadb.users WHERE id = " + id + " and username='" + username + "'";
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) user = this.create(rset);
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return user;
	}
	
	@Override
	public User getByEmail(String email) throws SQLException {
		var user = new User();
		this.conn = MysqlConnection.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT * FROM viculturadb.users WHERE email = '" + email + "'";
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) user = this.create(rset);
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return user;
	}
	
	@Override
	public User add(User user) throws SQLException {
		this.conn = MysqlConnection.getConnection();
		try {
			var query = "INSERT INTO viculturadb.users Values(?, ?, ?, ?, ?);";
			PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, user.getUsername());
			preparedStmt.setString(3, user.getEmail());
			preparedStmt.setString(4, user.getPassword());
			preparedStmt.setTimestamp(5, Timestamp.valueOf(user.getDateCreated()));
			preparedStmt.executeUpdate();
			
			ResultSet rs = preparedStmt.getGeneratedKeys();
			while(rs.next()) {
				user.setId(rs.getInt(1));
			}
			
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return user;
	}

	@Override
	public void update(User user) throws SQLException {
		this.conn = MysqlConnection.getConnection();
		try {
			var query = "UPDATE viculturadb.users SET username = ?, email = ? where id = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, user.getUsername());
			preparedStmt.setString(2, user.getEmail());
			preparedStmt.setInt(3, user.getId());
			preparedStmt.executeUpdate();
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
	}
	
	@Override
	public void updatePassword(String password, int idUser) throws SQLException {
		// TODO Auto-generated method stub
		this.conn = MysqlConnection.getConnection();
		try {
			var query = "UPDATE viculturadb.users SET password = ? where id = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, password);
			preparedStmt.setInt(2, idUser);
			preparedStmt.executeUpdate();
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
	}
	
	private User create(ResultSet rset) throws SQLException {
		var user = new User();
		user.setId(rset.getInt("id"));
		user.setUsername(rset.getString("username"));
		user.setEmail(rset.getString("email"));
		user.setDateCreated(rset.getTimestamp("date_created").toLocalDateTime());
		return user;
	}

}
