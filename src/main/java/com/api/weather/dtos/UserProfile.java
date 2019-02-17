package com.api.weather.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserProfile {
	private int id;
	private String name;
	private String mobile;
	private String email;
	private String city;
	private String country;
}
