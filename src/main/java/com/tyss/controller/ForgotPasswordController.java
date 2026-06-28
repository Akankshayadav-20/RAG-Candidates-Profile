package com.tyss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.tyss.dto.ForgotPasswordRequest;
import com.tyss.dto.ResetPasswordRequest;
import com.tyss.service.UserService;

@Controller
public class ForgotPasswordController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/forgot-password")
	public String forgotPassword(ForgotPasswordRequest request, Model model) {
		
		String message = userService.forgotPassword(request);
		
		if(message.equals("Email Verified")) {
			model.addAttribute("email", request.getEmail());
			
			return "html/reset-password";
		}
		
		model.addAttribute("error", message);
		
		return "html/forgot-password";
		
	}
	
	@PostMapping("/reset-password")
	 public String resetPassword(ResetPasswordRequest request, Model model) {
		
		String message = userService.resetPassword(request);
		
		if(message.equals("Password Updated Successfully")) {
			return "redirect:/login";
		}
		
		model.addAttribute("email", request.getEmail());
		model.addAttribute("error", message);
		
		return "html/reset-password";
	}
}
