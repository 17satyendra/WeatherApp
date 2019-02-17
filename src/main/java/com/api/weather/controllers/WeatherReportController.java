package com.api.weather.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.weather.models.SearchHistory;
import com.api.weather.models.WeatherData;
import com.api.weather.services.WeatherService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * This is a Rest Controller for Weather Details By Given Location With
 * {@link RestController @RestController}, we have added all general purpose
 * methods here those method will accept a rest request in JSON form and will
 * return a JSON response.
 * </p>
 * <p>
 * The methods are self explanatory we have used <b>{@code @RestController}</b>
 * annotation to point incoming requests to this class, and
 * <b>{@link ResponseBody @ResponseBody}</b> annotation to point incoming
 * requests to appropriate Methods. <b>{@link RequestBody @RequestBody}</b>
 * annotation is used to accept data with request in JSON form and Spring
 * ResponseEntity is used to return JSON as response to incoming request.
 * </p>
 * 
 * @author satyendra
 */
@RestController
@RequestMapping("/api/data/weather")
@Slf4j
public class WeatherReportController {
	
	@Autowired
	private WeatherService weatherService;
	
	/**
	 * This Rest API is use to get Weather details by Location using lat and lon.
	 * 
	 * @param lat
	 *            Latitude
	 * @param lon
	 *            Longitude
	 * @param token
	 *            Authorization valid token
	 * @return Weather Data
	 * @throws Exception
	 *             If token not valid
	 */
	@GetMapping
	public ResponseEntity<WeatherData> getWeatherDetailsByLocation(@RequestParam Double lat, 
			@RequestParam Double lon, @RequestHeader("Authorization") String token) throws Exception{
		log.info("Get Weather Details By lat: {}, and lon: {}", lat, lon);
		WeatherData data = weatherService.getWeatherDetailsBylocation(lat, lon, token);
		return new ResponseEntity<>(data, HttpStatus.OK);	
	}

	/**
	 * This rest API is use to get search history for loggedIn user
	 * 
	 * @param token
	 *            Authorization valid token
	 * @return List of Search history
	 * @throws Exception
	 *             If token is not valid.
	 */
	@GetMapping("/histroy")
	public ResponseEntity<List<SearchHistory>> getHistories(@RequestHeader("Authorization") String token) 
			throws Exception{
		log.info("Get search history");
		List<SearchHistory> histories = weatherService.getHistories(token);
		return new ResponseEntity<>(histories, HttpStatus.OK);
	}
}
