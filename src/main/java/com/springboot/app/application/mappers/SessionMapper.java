package com.springboot.app.application.mappers;

import com.springboot.app.application.dtos.SessionDto;
import com.springboot.app.domain.entities.Session;

public class SessionMapper {
	
	public Session createEntity(SessionDto dto) {
		var session = new Session();
		session.setToken(dto.getToken());
		session.setDateExpiration(dto.getDateExpiration());
		session.setUser(dto.getIdUser());
		return session;
	}
	
	public SessionDto createDto(Session session) {
		var dto = new SessionDto();
		dto.setToken(session.getToken());
		dto.setDateExpiration(session.getDateExpiration());
		dto.setIdUser(session.getUser());
		return dto;
	}
}
