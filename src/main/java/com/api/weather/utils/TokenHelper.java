package com.api.weather.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.api.weather.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenHelper {

	@Value("${spring.application.name}")
	private String APP_NAME;

	@Value("${jwt.secret}")
	public String secret;

	@Value("${jwt.header}")
	private String AUTH_HEADER;
	private static final String subject = "authToken";
	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;
	
	public String generateToken(User user) {
		log.info("inside generateToken()");
		Map<String, Object> map = new HashMap<>();
		map.put("id", user.getId());
		map.put("email", user.getEmail());
		return Jwts.builder()
				   .setIssuer(APP_NAME)
				   .setSubject(subject)
				   .setClaims(map)
				   .setIssuedAt(new Date())
				   .signWith(SIGNATURE_ALGORITHM, secret)
				   .compact();
	}

	private Claims getAllClaimsFromToken(String authToken) throws Exception {
		log.info("inside getAllClaimsFromToken()");
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody();
		} catch (Exception e) {
			throw new Exception("Invalid Token");
		}
		return claims;
	}
	
	public Integer getUserIdFromToken(String token) throws Exception {
		Claims claims = getAllClaimsFromToken(token);
		return (Integer)claims.get("id");
	}

	public String getToken(HttpServletRequest request) {
		log.info("inside getAuthHeaderFromHeader()");
		return request.getHeader("Authorization");
	}

	public boolean isValid(String authToken)  {
		boolean isValid = true;
		try {
			getAllClaimsFromToken(authToken);
		} catch (Exception e) {
			isValid = false;
			log.error(e.getMessage(), e);
		}
		return isValid;
	}

}
