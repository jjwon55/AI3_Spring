package com.aloha.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aloha.spring.dto.User;
import com.aloha.spring.service.UserService;

@Controller
public class UserController {

	private static final Logger logger
					= LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/join")
	public void join(@ModelAttribute User user){
		
	}
	
	@PostMapping("/join")
	public String joinPost(@Validated @ModelAttribute User user, BindingResult bindingResult) throws Exception {
		
		if(bindingResult.hasErrors()) {
			return "join";
		}
		
		boolean result = userService.join(user);
		
		if(result)
			return "redirect:/";
		else 
			return "join";
	}
	
	
}
