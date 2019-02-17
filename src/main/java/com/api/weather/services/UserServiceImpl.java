package com.api.weather.services;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.weather.dtos.UserDTO;
import com.api.weather.dtos.UserProfile;
import com.api.weather.models.User;
import com.api.weather.repository.UserRepository;
import com.api.weather.utils.TokenHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>This is implemented class of user service.</p> 
 * @author satyendra
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TokenHelper tokenHelper;

	@Override
	@Transactional
	public int save(UserDTO userDTO) throws Exception {
		log.info("Inside User Service trying save");
		boolean isPresent = !userRepository.findByEmail(userDTO.getEmail()).isPresent();
		if(isPresent) {
			throw new Exception(String.format("User Already Register With Email: %s", userDTO.getEmail()));
		}
		User user = objectMapper(userDTO, new User());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user).getId();
	}
	
	@Override
	@Transactional(readOnly=true)
	public UserProfile getUserProfile(String token) throws Exception {
		log.info("Inside User Service trying get User Profile");
		Integer userId = tokenHelper.getUserIdFromToken(token);
		return userRepository.findById(userId).map(user->this.objectMapper(user, new UserProfile()))
				                              .orElseThrow(()-> new Exception("User not found"));
	}
	
	@Override
	public String login(Map<String, String> userCredential) throws Exception {
		log.info("Inside user Service for user authentication");
		String emailId = userCredential.get("email");
		return userRepository.findByEmail(emailId)
							 .filter(user->
							 	passwordEncoder.matches(userCredential.get("password"), 
							 			user.getPassword()))
							 .map(tokenHelper::generateToken)
							 .orElseThrow(()->new Exception("Bad Credential"));
	}
	
	private <T, E> E objectMapper(T t, E e) {
		BeanUtils.copyProperties(t, e);
		return e;
	}
}
