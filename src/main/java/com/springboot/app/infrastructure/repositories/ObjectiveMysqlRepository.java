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

import com.springboot.app.application.interfaces.ObjectiveRepository;
import com.springboot.app.domain.entities.Material;
import com.springboot.app.domain.entities.Objective;
import com.springboot.app.infrastructure.MysqlConnection;

@Repository
public class ObjectiveMysqlRepository implements ObjectiveRepository {

	private Connection conn;
	
	public ObjectiveMysqlRepository() {
		
	}
	
	@Override
	public List<Objective> getAllByIdGroup(int idGroup, int idUser, int start, int limit) throws SQLException {
		var objectives = new ArrayList<Objective>();
		this.conn = MysqlConnection.getConnection();
			
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT o.id, m.name, m.year, m.url_image, m.url_details, o.min_progress, o.max_progress, "
					+ "o.current_progress, o.date_created, o.id_group "
				+ "FROM objectives as o, materials as m, viculturadb.groups as g "
				+ "WHERE o.id_group = " + idGroup + " and o.id_material = m.id and o.id_group = g.id and g.id_user = " + idUser
				+ " LIMIT " + start + ", " + limit;
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) objectives.add(this.createObjectiveForGetAllByIdGroup(rset));
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return objectives;
	}
	
	@Override
	public int getTotalByIdGroup(int idGroup) throws SQLException {
		int total = 0;
		this.conn = MysqlConnection.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT count(*) as total from objectives WHERE id_group = " + idGroup;
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) total = rset.getInt("total");
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return total;
	}

	@Override
	public Objective add(Objective objective) throws SQLException {
		this.conn = MysqlConnection.getConnection();
		try {
			var query = "INSERT INTO viculturadb.objectives Values(?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2, objective.getMinProgress());
			preparedStmt.setInt(3, objective.getMaxProgress());
			preparedStmt.setTimestamp(4, Timestamp.valueOf(objective.getDateCreated()));
			preparedStmt.setInt(5, objective.getIdGroup());
			preparedStmt.setInt(6, objective.getMaterial().getId());
			preparedStmt.setInt(7, objective.getCurrentProgress());
			preparedStmt.executeUpdate();
			ResultSet rs = preparedStmt.getGeneratedKeys();
			while(rs.next()) {
				objective.setId(rs.getInt(1));
			}
			
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return objective;
	}
	
	@Override 
	public void update(Objective objective) throws SQLException {
		this.conn = MysqlConnection.getConnection();
		try {
			var query = "UPDATE viculturadb.objectives SET min_progress = ?, max_progress = ?, current_progress = ? where id = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, objective.getMinProgress());
			preparedStmt.setInt(2, objective.getMaxProgress());
			preparedStmt.setInt(3, objective.getCurrentProgress());
			preparedStmt.setInt(4, objective.getId());
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
	
	private Objective createObjectiveForGetAllByIdGroup(ResultSet rset) throws SQLException {
		var objective = new Objective();
		var material = new Material();
		
		material.setName(rset.getString("name"));
		material.setYear(rset.getInt("year"));
		material.setUrlImage(rset.getString("url_image"));
		material.setUrlDetails(rset.getString("url_details"));
		
		objective.setMinProgress(rset.getInt("min_progress"));
		objective.setMaxProgress(rset.getInt("max_progress"));
		objective.setCurrentProgress(rset.getInt("current_progress"));
		objective.setDateCreated(rset.getTimestamp("date_created").toLocalDateTime());
		objective.setIdGroup(rset.getInt("id_group"));
		objective.setMaterial(material);
		return objective;
	}
}
