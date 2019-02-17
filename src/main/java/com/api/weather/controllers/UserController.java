package com.api.weather.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.weather.dtos.UserDTO;
import com.api.weather.dtos.UserProfile;
import com.api.weather.responses.Response;
import com.api.weather.responses.UserFieldErrors;
import com.api.weather.services.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * This is a Rest Controller for User With
 * {@link RestController @RestController}, we have added all general purpose
 * methods here those method will accept a rest request in JSON form and will
 * return a JSON response.
 * </p>
 * <p>
 * The methods are self explanatory we have used <b>{@code @RestController}</b>
 * annotation to point incoming requests to this class, and
 * <b>{@link ResponseBody @ResponseBody}</b> annotation to point incoming
 * requests to appropriate Methods. <b>{@link RequestBody @RequestBody}</b>
 * annotation is used to accept data with request in JSON form and Spring
 * ResponseEntity is used to return JSON as response to incoming request.
 * </p>
 * 
 * @author satyendra
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * <p>
	 * This rest API for new user registration/save With
	 * {@link RequestMapping @RequestMapping} to mapped rest address.
	 * </p>
	 * 
	 * @param userDTO
	 *            Object to be save, should not be null.
	 * @param result
	 *            Binding Error Result
	 * @return ResponseEntity with HTTP status and message.
	 * @throws Exception 
	 * 			  If User Already Register with given Email Id. 
	 */
	@PostMapping
	public ResponseEntity<Response> save(@Valid @RequestBody UserDTO userDTO, BindingResult result) 
			throws Exception {
		log.info("Saving User Data with : {}", userDTO.toString());

		if (result.hasErrors()) {
			log.error("User Registration validation error {}", result.getFieldErrors());
			UserFieldErrors response = new UserFieldErrors(112, "Validation Error", result.getFieldErrors());
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		int id = userService.save(userDTO);
		String msg = String.format("User Data Saved with id : %d", id);
		return new ResponseEntity<>(new Response(200, msg), HttpStatus.OK);
	}

	/**
	 * <p>This RestApi for get User profile By given valid user Id</p> 
	 * @param userId
	 *            should not be null
	 * @return User profile by given Id.
	 * @throws Exception
	 *             If user not found with given Id.
	 */
	@GetMapping("profile")
	public ResponseEntity<UserProfile> getUserProfile(@RequestHeader("Authorization") String token) throws Exception {
		log.info("Get User Profile with user token : {}", token);
		UserProfile profile = userService.getUserProfile(token);
		return new ResponseEntity<>(profile, HttpStatus.OK);
	}
	
	/**
	 * <p>
	 * This is simple login rest API where validate with valid existing user from
	 * DB. If user found then give successful response with JWT token. If not found
	 * or some thing other, then return appropriate response.
	 * </p>
	 * 
	 * @param userCredential
	 *            UserCredential contains user email Id and password.
	 * @return ResponseEntity with HTTP status and message.
	 * @throws Exception
	 *             If Bad Credential.
	 */
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody Map<String, String> userCredential,
			HttpServletResponse resp) throws Exception{
		log.info("Do login");
		String token = userService.login(userCredential);
		resp.setHeader("Authorization", token);
		return new ResponseEntity<>(new Response(200, "Login successfull"), HttpStatus.OK);
	}
}
