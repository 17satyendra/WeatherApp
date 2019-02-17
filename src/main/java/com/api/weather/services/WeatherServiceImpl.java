package com.api.weather.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.api.weather.models.SearchHistory;
import com.api.weather.models.User;
import com.api.weather.models.WeatherData;
import com.api.weather.utils.TokenHelper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WeatherServiceImpl implements WeatherService{

	@Autowired
	private TokenHelper tokenHelper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${weather.service.base.url}")
	private String weatherBaseURL;
	
	@Autowired
	private WeatherDataRepository weatherDataRepository;
	
	@Autowired
	private SearchHistoryRepository historyRepository;
	
	@Value("${weather.app.id}")
	private String appId;
	 
	@Override
	@Transactional
	public WeatherData getWeatherDetailsBylocation(Double lat, Double lon, String token) throws Exception {
		log.info("Inside Weather service Impl for get Weather data");
		Integer userId = tokenHelper.getUserIdFromToken(token);
		return getWeatherDetails(lat, lon).map(wd->this.saveWeatherData(wd, userId))
								   .orElseThrow(()-> new Exception("Service Down Please Try after some time"));
	}

	private Optional<WeatherData> getWeatherDetails(Double lat, Double lon) {
		String URL = UriComponentsBuilder.fromUriString(weatherBaseURL)
							.queryParam("lat", lat)
							.queryParam("lon", lon)
							.queryParam("appid", appId).build().toString();
		log.warn("Calling Weather service with URL: {}", URL);	
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		WeatherData data = restTemplate.exchange(URL, HttpMethod.GET, null,WeatherData.class).getBody();
		return Optional.ofNullable(data);
	}
	private WeatherData saveWeatherData(WeatherData data, Integer userId) {
		data.setUser(new User(userId));
		weatherDataRepository.save(data);
		saveSearchHistory(data, userId);
		return data;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<SearchHistory> getHistories(String token) throws Exception {
		log.info("Inside Weather service Impl for get search history");
		Integer userId = tokenHelper.getUserIdFromToken(token);
		List<SearchHistory> histories = new ArrayList<>();
		histories = historyRepository.findAllByUserId(userId);
		return histories;
	}
	private void saveSearchHistory(WeatherData data, Integer userId) {
		SearchHistory history = new SearchHistory(data, userId);
		historyRepository.save(history);
	}
}
