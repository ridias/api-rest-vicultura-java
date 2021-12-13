package com.springboot.app.application.interfaces;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.springboot.app.domain.entities.Group;

@NoRepositoryBean
public interface GroupRepository extends AddUpdate<Group>, Delete{

	public List<Group> getAllByUserId(int idUser) throws SQLException;
	public Group getById(int id) throws SQLException;
}
