package com.tyss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tyss.dto.RegisterRequest;
import com.tyss.service.UserService;

@Controller
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String home() {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
	    return "html/login";
	}

	@GetMapping("/register")
	public String register() {
	    return "html/register";
	}

	@GetMapping("/chat")
	public String chat() {
	    return "html/chat";
	}

	 @GetMapping("/forgot-password")
	    public String forgotPassword() {
	        return "html/forgot-password";
	    }
	 
	 @PostMapping("/register")
	 public String register(RegisterRequest request) {
		 String result = userService.register(request);
		 
		 if(result.equals("Registration Successful")) {
			 
			 return "redirect:/login";
		 }
		 
		 return "redirect:/register";
	 }
}
