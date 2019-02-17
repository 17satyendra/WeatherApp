package com.api.weather.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDTO {

	@NotNull(message = "*Please provide a Name")
	@Size(min = 3, message = "*name must have at least 3 characters")
	private String name;
	
	@NotNull(message = "*Please Provide Mobile number")
	@Size(min = 10, max = 10)
	@Pattern(groups = Pattern.class, regexp = "(^$|[0-9]{10})", message = "provide valid Mobile Number")
	private String mobile;
	
	@Email(message = "*Please provide a valid Email")
	@NotNull(message = "*Please provide an email")
	private String email;

	@NotNull(message = "*Please provide a Name")
	@Size(min = 3, message = "*name must have at least 3 characters")
	private String city;

	@NotNull(message = "*Please provide a Name")
	@Size(min = 3, message = "*name must have at least 3 characters")
	private String country;
	
	@NotNull(message = "*Please Provide Password")
	@Size(min = 3, message = "*Password must have at least 3 character")
	@Pattern(groups = Pattern.class, regexp = "^((?=.*\\d)(?=.*[a-zA-Z])(?=.*[@#$%!]).{3,40})$", message = "Provide at least one letter and one number")
	private String password;
}
