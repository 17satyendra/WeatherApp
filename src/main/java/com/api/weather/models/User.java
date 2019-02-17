package com.api.weather.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is Simple User Entity
 * @author Satyendra 
 */
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{

	public User(Integer id) {
		this.id=id;
	}
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "user", strategy = "increment")
	@GeneratedValue(generator = "user")
	private int id;
	private String name;
	private String mobile;
	@UniqueElements
	private String email;
	private String city;
	private String country;
	private String password;
}
