package com.tyss.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tyss.dto.ForgotPasswordRequest;
import com.tyss.dto.LoginRequest;
import com.tyss.dto.RegisterRequest;
import com.tyss.dto.ResetPasswordRequest;
import com.tyss.entity.User;
import com.tyss.repository.UserRepository;
import com.tyss.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }

        if(!request.getPassword().equals(request.getConfirmPassword())) {
            return "Passwords do not match";
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setProvider("LOCAL");

        userRepository.save(user);

        return "Registration Successful";
    }

    @Override
    public String login(LoginRequest request) {

        Optional<User> optionalUser =
                userRepository.findByEmail(request.getEmail());

        if(optionalUser.isEmpty()) {
            return "Invalid Email";
        }

        User user = optionalUser.get();

        if(!passwordEncoder.matches(request.getPassword(),
                                    user.getPassword())) {
            return "Invalid Password";
        }

        return "Login Successful";
    }

	@Override
	public String forgotPassword(ForgotPasswordRequest request) {
		if(!userRepository.existsByEmail(request.getEmail())) {
			
			return "Email not registered";
		}
		return "Email Verified";
	}

	@Override
	public String resetPassword(ResetPasswordRequest request) {
		
		if(!request.getNewPassword().equals(request.getConfirmPassword())) {
			
			return "Password do not match";
		}
		
		User user = userRepository.findByEmail(request.getEmail()).orElse(null);
		
		if(user == null) {
			return "User not found";
		}
		
		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		
		userRepository.save(user);
		return "Password Updated Successfully";
	}
    
    

}