package com.api.weather.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
@Entity
@Table
public class SearchHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "history", strategy = "increment")
	@GeneratedValue(generator = "history")
	private int id;
	private LocalDateTime createrStamp = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.UTC));
	private int userId;
	@OneToOne
	private WeatherData data;
	public SearchHistory(WeatherData data, Integer userId) {
		this.data = data;
		this.userId = userId;
	}
}
