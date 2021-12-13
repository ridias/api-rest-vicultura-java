package com.springboot.app.application.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.springboot.app.domain.entities.Objective;

public interface ObjectiveRepository extends AddUpdate<Objective>, Delete{

	public List<Objective> getAllByIdGroup(int idGroup, int idUser, int start, int limit) throws SQLException;
	public int getTotalByIdGroup(int idGroup) throws SQLException;
}
