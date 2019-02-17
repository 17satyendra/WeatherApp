package com.api.weather.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter @Getter @ToString
@Embeddable
public class TempData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private double temp;
	private int pressure;
	private int humidity;
	private double temp_min;
	private double temp_max;

}
