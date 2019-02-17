package com.api.weather.responses;

import java.util.List;

import org.springframework.validation.FieldError;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class for user field validation error.
 * @author satyendra
 */
@Setter @Getter @ToString
public class UserFieldErrors extends Response{
	private List<FieldError> fieldErrors;
	public UserFieldErrors(int code, String message, List<FieldError> fieldErrors) {
		super(code, message);
		this.fieldErrors=fieldErrors;
	}

}
