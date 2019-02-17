package com.api.weather.services;

import java.util.List;

import com.api.weather.models.SearchHistory;
import com.api.weather.models.WeatherData;

public interface WeatherService {

	WeatherData getWeatherDetailsBylocation(Double lat, Double lon, String token) throws Exception;

	List<SearchHistory> getHistories(String token) throws Exception;

}
