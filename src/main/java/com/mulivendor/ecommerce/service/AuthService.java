package com.mulivendor.ecommerce.service;

import com.mulivendor.ecommerce.request.LoginRequest;
import com.mulivendor.ecommerce.request.SignupRequest;
import com.mulivendor.ecommerce.response.AuthResponse;

public interface AuthService {

    void sentLoginSignupOtp(String email) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signing(LoginRequest request);
}
//Before there is method for createUser which is working fine
//For signup and login otp : 04:47:10
//For signing : 05:10:00