package com.aloha.spring.dao;

import com.aloha.spring.dto.Person;

import lombok.Data;

@Data
public class PersonDTO {

	private Person person;
	
	private String major;
}
