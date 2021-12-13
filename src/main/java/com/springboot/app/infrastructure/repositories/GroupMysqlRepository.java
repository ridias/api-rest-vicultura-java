package com.springboot.app.infrastructure.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.springboot.app.application.interfaces.GroupRepository;
import com.springboot.app.domain.entities.Group;
import com.springboot.app.infrastructure.MysqlConnection;


@Repository
public class GroupMysqlRepository implements GroupRepository{

	private Connection conn;
	
	public GroupMysqlRepository() {
		
	}
	
	@Override
	public List<Group> getAllByUserId(int idUser) throws SQLException{
		var groups = new ArrayList<Group>();
		this.conn = MysqlConnection.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT * FROM viculturadb.groups WHERE id_user = " + idUser;
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) groups.add(this.create(rset));
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return groups;
	}

	@Override
	public Group getById(int id) throws SQLException {
		var group = new Group();
		this.conn = MysqlConnection.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT * FROM viculturadb.groups WHERE id = " + id;
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) group = this.create(rset);
		}catch(Exception ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return group;
	}

	@Override
	public Group add(Group group) throws SQLException{
		this.conn = MysqlConnection.getConnection();
		try {
			var query = "INSERT INTO viculturadb.groups Values(?, ?, ?, ?, ?, ?);";
			PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, group.getName());
			preparedStmt.setString(3, group.getDescription());
			preparedStmt.setTimestamp(4, Timestamp.valueOf(group.getDateCreated()));
			preparedStmt.setTimestamp(5, null);
			preparedStmt.setInt(6, group.getIdUser());
			preparedStmt.executeUpdate();
			
			ResultSet rs = preparedStmt.getGeneratedKeys();
			while(rs.next()) {
				group.setId(rs.getInt(1));
			}
			
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return group;
	}

	@Override
	public void update(Group group) throws SQLException{
		this.conn = MysqlConnection.getConnection();
		try {
			var query = "UPDATE viculturadb.groups SET name = ?, description = ?, date_end = ? where id = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, group.getName());
			preparedStmt.setString(2, group.getDescription());
			preparedStmt.setTimestamp(3, Timestamp.valueOf(group.getDateEnd()));
			preparedStmt.setInt(4, group.getIdUser());
			preparedStmt.executeUpdate();
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
	}

	@Override
	public void delete(int id) throws SQLException {
		this.conn = MysqlConnection.getConnection();
		try {
			var query = "DELETE FROM viculturadb.groups WHERE id = " + id;
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
	}
	
	private Group create(ResultSet rset) throws SQLException {
		var group = new Group();
		group.setId(rset.getInt("id"));
		group.setName(rset.getString("name"));
		group.setDescription(rset.getString("description"));
		group.setDateCreated(rset.getTimestamp("date_created").toLocalDateTime());
		group.setUser(rset.getInt("id_user"));
		return group;
	}

}
