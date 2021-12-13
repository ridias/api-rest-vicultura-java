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

import com.springboot.app.application.interfaces.MaterialRepository;
import com.springboot.app.domain.entities.Material;
import com.springboot.app.infrastructure.MysqlConnection;

@Repository
public class MaterialMysqlRepository implements MaterialRepository{

	private Connection conn;
	
	public MaterialMysqlRepository() {
		
	}
	
	@Override
	public List<Material> getAll(int start, int limit) throws SQLException {
		var materials = new ArrayList<Material>();
		this.conn = MysqlConnection.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT * FROM materials LIMIT " + start + ", " + limit;
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) materials.add(this.create(rset));
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return materials;
	}
	
	@Override
	public Material getById(int id) throws SQLException {
		var material = new Material();
		this.conn = MysqlConnection.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT * FROM viculturadb.materials WHERE id = " + id;
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) material = this.create(rset);
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return material;
	}

	@Override
	public Material getByName(String name) throws SQLException {
		var material = new Material();
		this.conn = MysqlConnection.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT * FROM viculturadb.materials WHERE LOWER(name) = " + name + ";";
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) material = this.create(rset);
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return material;
	}

	@Override
	public List<Material> searchNameByRegexp(String name, int limit) throws SQLException {
		var materials = new ArrayList<Material>();
		this.conn = MysqlConnection.getConnection();
		
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT * FROM materials WHERE LOWER(name) REGEXP '" + name + "' LIMIT " + limit;
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next() && materials.size() < limit) materials.add(this.create(rset));
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return materials;
	}
	
	@Override
	public int getTotal() throws SQLException {
		int total = 0;
		this.conn = MysqlConnection.getConnection();
		try {
			Statement stmt = conn.createStatement();
			var query = "SELECT count(*) as total from materials";
			ResultSet rset = stmt.executeQuery(query);
			while(rset.next()) total = rset.getInt("total");
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return total;
	}

	@Override
	public Material add(Material material) throws SQLException {
		this.conn = MysqlConnection.getConnection();
		try {
			var query = "INSERT INTO viculturadb.materials Values(?, ?, ?, ?, ?, ?);";
			PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, material.getName());
			preparedStmt.setInt(3, material.getYear());
			preparedStmt.setTimestamp(4, Timestamp.valueOf(material.getDateCreated()));
			preparedStmt.setString(5, material.getUrlImage());
			preparedStmt.setString(6, material.getUrlDetails());
			preparedStmt.executeUpdate();
			ResultSet rs = preparedStmt.getGeneratedKeys();
			while(rs.next()) {
				material.setId(rs.getInt(1));
			}
			
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
		return material;
	}

	@Override
	public void update(Material material) throws SQLException {
		this.conn = MysqlConnection.getConnection();
		try {
			var query = "UPDATE viculturadb.materials SET name = ?, year = ?, url_image = ?, url_details = ? where id = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, material.getName());
			preparedStmt.setInt(2, material.getYear());
			preparedStmt.setString(3, material.getUrlImage());
			preparedStmt.setString(4, material.getUrlDetails());
			preparedStmt.setInt(5, material.getId());
			preparedStmt.executeUpdate();
		}catch(SQLException ex) {
			throw ex;
		}
		
		MysqlConnection.closeConnection();
	}
	
	public Material create(ResultSet rset) throws SQLException {
		var material = new Material();
		material.setId(rset.getInt("id"));
		material.setName(rset.getString("name"));
		material.setYear(rset.getInt("year"));
		material.setDateCreated(rset.getTimestamp("date_created").toLocalDateTime());
		material.setUrlImage(rset.getString("url_image"));
		material.setUrlDetails(rset.getString("url_details"));
		return material;
		
	}

}
