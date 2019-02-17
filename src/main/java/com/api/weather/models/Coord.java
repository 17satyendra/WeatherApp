package com.api.weather.models;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
@Embeddable
public class Coord {
	private double lat;
	private double lon;
}
