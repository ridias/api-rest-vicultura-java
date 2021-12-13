package com.springboot.app.application.helpers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptHelper {

	private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public static String getHashedPassword(String password) {
		return encoder.encode(password);
	}
	
	public static boolean isHashedPasswordMatched(String hashedPassword, String password) {
		return encoder.matches(password, hashedPassword);
	}
}
