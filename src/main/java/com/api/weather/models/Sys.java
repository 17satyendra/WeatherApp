package com.api.weather.models;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter @Getter @ToString
@Embeddable
public class Sys implements Serializable{

	private static final long serialVersionUID = 1L;
	private double message;
    private String country;
}
