package com.api.weather.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.api.weather.security.TokenAuthenticationFilter;
import com.api.weather.utils.TokenHelper;

@Configuration
public class ApplicationConfig {
	
	@Autowired
	TokenHelper tokenHelper;

	@Bean
	public TokenAuthenticationFilter getFilter() {
		return new TokenAuthenticationFilter(tokenHelper);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public FilterRegistrationBean<TokenAuthenticationFilter> myFilterBean() {
		final FilterRegistrationBean<TokenAuthenticationFilter> filterRegBean = new FilterRegistrationBean<>();
		filterRegBean.setFilter(getFilter());
		filterRegBean.setEnabled(Boolean.TRUE);
		filterRegBean.addUrlPatterns("/api/user/profile");
		filterRegBean.addUrlPatterns("/api/data/weather/**");
		filterRegBean.setName("My Filter");
		filterRegBean.setAsyncSupported(Boolean.TRUE);
		filterRegBean.setOrder(1);
		return filterRegBean;
	}
	@Bean
	public RestTemplate geTemplate() {
		return new RestTemplate();
	}
	
	
}
	
