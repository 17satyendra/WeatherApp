package com.api.weather.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.weather.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String emailId);

}
