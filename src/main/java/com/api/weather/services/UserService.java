package com.api.weather.services;

import java.util.Map;

import com.api.weather.dtos.UserDTO;
import com.api.weather.dtos.UserProfile;

public interface UserService {

	int save(UserDTO userDTO) throws Exception;

	UserProfile getUserProfile(String token) throws Exception;

	String login(Map<String, String> userCredential) throws Exception;

}
