package com.api.weather.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.weather.models.WeatherData;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Integer> {

}
