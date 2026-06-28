package com.tyss.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.tyss.entity.User;
import com.tyss.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler{
 
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			                            HttpServletResponse response,
			                            Authentication authentication)
	throws IOException, ServletException{
		DefaultOAuth2User oauthUser =   (DefaultOAuth2User) authentication.getPrincipal();
		
		String name = oauthUser.getAttribute("name");
		String email = oauthUser.getAttribute("email");
		
		if(!userRepository.existsByEmail(email)) {
			
			User user = new User();
			user.setUsername(name);
			user.setEmail(email);
			
			user.setPassword("GOOGLE_LOGIN");
			user.setRole("USER");
			user.setProvider("GOOGLE");
			
			userRepository.save(user);
		}
		response.sendRedirect(request.getContextPath() + "/chat");
	}
}
