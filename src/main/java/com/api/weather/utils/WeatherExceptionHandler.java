package com.api.weather.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.api.weather.responses.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class WeatherExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception e){
		log.error(e.getMessage(), e);
		Response response = new Response(-100, e.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
