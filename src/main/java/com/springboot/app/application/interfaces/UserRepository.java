package com.springboot.app.application.interfaces;

import java.sql.SQLException;

import com.springboot.app.domain.entities.User;

public interface UserRepository extends AddUpdate<User>{
	
	public User getById(int id) throws SQLException;
	public User getByEmail(String email) throws SQLException;
	public User getByUsername(String username) throws SQLException;
	public User getByUsernameAndId(int id, String username) throws SQLException;
	public void updatePassword(String password, int idUser) throws SQLException;
}
