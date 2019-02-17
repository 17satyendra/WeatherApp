package com.api.weather.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.weather.utils.TokenHelper;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private TokenHelper tokenHelper;
	
	public TokenAuthenticationFilter( TokenHelper tokenHelper ) 
	{
		log.info("Created "+ getClass().getSimpleName());
		this.tokenHelper = tokenHelper;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		log.info("init method for filter");
		
		String authToken = tokenHelper.getToken(request);
		log.info(authToken);
		if( authToken == null )
		{
			log.error("Token not found");
			response.sendError( HttpServletResponse.SC_BAD_REQUEST, "Token not found" );
			return;
		}
			
		boolean isvalid = tokenHelper.isValid(authToken);
		if( !isvalid)
		{
			log.warn("Token is not valid");
			response.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Token is not valid" );
			return;
		}
		chain.doFilter(request, response);
	}

}
