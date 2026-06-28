package com.tyss.service;

import com.tyss.dto.ForgotPasswordRequest;
import com.tyss.dto.LoginRequest;
import com.tyss.dto.RegisterRequest;
import com.tyss.dto.ResetPasswordRequest;

public interface UserService {

    String register(RegisterRequest request);

    String login(LoginRequest request);
    
    String forgotPassword(ForgotPasswordRequest request);
    
    String resetPassword(ResetPasswordRequest request);

}
