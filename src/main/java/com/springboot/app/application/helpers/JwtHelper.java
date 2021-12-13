package com.springboot.app.application.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.springboot.app.application.dtos.UserTokenDetailsDto;

public class JwtHelper {

	private static String secret = "itsviculturasecret";
	
	public static String generateToken(String username, int idUser) {
		Map<String, String> values = new HashMap<String, String>();
		values.put("username", username);
		values.put("id", idUser + "");
		Algorithm algorithm = Algorithm.HMAC256(secret);
		String access_token = JWT.create()
				.withSubject(username)
				.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
				.withClaim("user-details", values)
				.sign(algorithm);
		
		return access_token;
	}
	
	public static UserTokenDetailsDto decodeToken(String token) {
		if(token == null) return null;
		var userTokenDetails = new UserTokenDetailsDto();
		userTokenDetails.setToken(token);
		
		var claims = JWT.decode(token).getClaim("user-details");
		userTokenDetails.setUsername(claims.asMap().get("username").toString());
		userTokenDetails.setId(Integer.parseInt(claims.asMap().get("id").toString()));
		
		return userTokenDetails;
	}
}
