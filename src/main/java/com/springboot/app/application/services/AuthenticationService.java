package com.springboot.app.application.services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.app.application.dtos.RequestDto;
import com.springboot.app.application.dtos.ResponseDto;
import com.springboot.app.application.dtos.SessionDto;
import com.springboot.app.application.dtos.UserLoginDto;
import com.springboot.app.application.dtos.UserRegisterDto;
import com.springboot.app.application.exceptions.DatabaseException;
import com.springboot.app.application.exceptions.InvalidCredentials;
import com.springboot.app.application.exceptions.InvalidParameter;
import com.springboot.app.application.exceptions.InvalidRequest;
import com.springboot.app.application.helpers.BCryptHelper;
import com.springboot.app.application.helpers.GeneratorResponseHelper;
import com.springboot.app.application.helpers.JwtHelper;
import com.springboot.app.application.interfaces.SessionRepository;
import com.springboot.app.application.interfaces.UserRepository;
import com.springboot.app.application.mappers.SessionMapper;
import com.springboot.app.application.mappers.UserLoginMapper;
import com.springboot.app.application.mappers.UserRegisterMapper;
import com.springboot.app.domain.entities.Session;
import com.springboot.app.domain.entities.User;
import com.springboot.app.domain.validators.UserValidator;

@Service
public class AuthenticationService {

	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	private UserValidator userValidator;
	
	private UserRegisterMapper registerMapper;
	private UserLoginMapper loginMapper;
	private SessionMapper sessionMapper;
	private GeneratorResponseHelper<SessionDto> generateResponse;
	
	public AuthenticationService(SessionRepository session, UserRepository user, UserValidator validator) {
		this.sessionRepository = session;
		this.userRepository = user;
		this.userValidator = validator;
		
		this.registerMapper = new UserRegisterMapper();
		this.loginMapper = new UserLoginMapper();
		this.sessionMapper = new SessionMapper();
		this.generateResponse = new GeneratorResponseHelper<SessionDto>();
	}
	
	public ResponseDto<SessionDto> register(RequestDto<UserRegisterDto> request) {
		if(request == null) 
			return this.generateResponse.fail(new InvalidRequest("The request is null, something is wrong!"));
		
		var userDetails = request.body;
		if(userDetails == null) 
			return this.generateResponse.fail(new InvalidParameter("The user details is null, something is wrong!"));
		
		User userToRegister = this.registerMapper.createEntity(userDetails);
		userToRegister.setDateCreated(LocalDateTime.now());
		
		if(!this.userValidator.isValidWithoutCheckingId(userToRegister)) {
			var messageError = this.userValidator.getMessageError(userToRegister);
			return this.generateResponse.fail(new InvalidParameter(messageError));
		}
		
		try {
			var duplicateUser = this.userRepository.getByUsername(userToRegister.getUsername());
			var duplicateEmail = this.userRepository.getByEmail(userToRegister.getEmail());
			if(duplicateUser.getId() != -1 || duplicateEmail.getId() != -1)
				return this.generateResponse.fail(new InvalidParameter("The username or email is already used!"));
			
			userToRegister.setPassword(BCryptHelper.getHashedPassword(userToRegister.getPassword()));
			this.userRepository.add(userToRegister);
			return this.generateResponse.ok(new ArrayList<SessionDto>());
		}catch(SQLException ex) {
			return this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}
	}
	
	public ResponseDto<SessionDto> login(RequestDto<UserLoginDto> request) {
		if(request == null) 
			return this.generateResponse.fail(new InvalidRequest("The request is null, something is wrong!"));
		
		var userDetails = request.body;
		if(userDetails == null) 
			return this.generateResponse.fail(new InvalidParameter("The user details is null, something is wrong!"));
		
		User userToAuthenticate = this.loginMapper.createEntity(userDetails);
		User user = null;
		
		try {
			user = this.userRepository.getByUsername(userToAuthenticate.getUsername());
			if(!BCryptHelper.isHashedPasswordMatched(user.getPassword(), userToAuthenticate.getPassword()))
				return this.generateResponse.fail(new InvalidCredentials());
			
			String token = JwtHelper.generateToken(user.getUsername(), user.getId());
			Session session = new Session();
			session.setToken(token);
			session.setUser(user.getId());
			session.setDateCreated(LocalDateTime.now());
			session.setDateExpiration(LocalDateTime.now().plus(86400 * 7 * 1000, ChronoField.MILLI_OF_DAY.getBaseUnit()));
			
			this.sessionRepository.add(session);
			var sessions = new ArrayList<SessionDto>();
			sessions.add(this.sessionMapper.createDto(session));
			return this.generateResponse.ok(sessions);
		}catch(SQLException ex) {
			return this.generateResponse.fail(new DatabaseException(ex.getMessage()));
		}
	}
	
	public boolean isTokenValid(String token) {
		if(token == null) return false;
		
		var userTokenDetails = JwtHelper.decodeToken(token);
		if(userTokenDetails.getId() <= 0 || userTokenDetails.getUsername() == "" || userTokenDetails.getUsername() == null) 
			return false;
		
		User user = null;
		Session lastSession = null;
		try {
			user = this.userRepository.getByUsernameAndId(userTokenDetails.getId(), userTokenDetails.getUsername());
			if(user.getId() == -1) return false;
			lastSession = this.sessionRepository.getToken(user.getId());
		}catch(SQLException ex) {
			return false;
		}
		
		String tokenFromLastSession = lastSession.getToken();
		if(!tokenFromLastSession.equals(userTokenDetails.getToken())) return false;

		var now = LocalDateTime.now();
		if(now.isAfter(lastSession.getDateExpiration())) return false;
		return true;
	}
}
