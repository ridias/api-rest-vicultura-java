package com.springboot.app.application.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.springboot.app.domain.entities.Material;

public interface MaterialRepository extends AddUpdate<Material>{
	
	public int getTotal() throws SQLException;
	public List<Material> getAll(int start, int limit) throws SQLException;
	public Material getById(int id) throws SQLException;
	public Material getByName(String name) throws SQLException;
	public List<Material> searchNameByRegexp(String name, int limit) throws SQLException;
}
