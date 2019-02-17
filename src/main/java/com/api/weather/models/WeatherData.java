package com.api.weather.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
@Entity
@Table
public class WeatherData implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	private String name;
	private LocalDateTime createStamp= LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.UTC));
	@Embedded
	private TempData main;
	private int visibility;
	@Embedded
	private Coord coord;
	@Embedded
	private Wind wind;
	@Embedded
	private Sys sys;
	@JsonIgnore
	@OneToOne
	private User user;
}
