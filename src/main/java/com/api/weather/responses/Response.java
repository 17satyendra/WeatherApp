package com.api.weather.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Response {
	private int code;
	private String message;
}
