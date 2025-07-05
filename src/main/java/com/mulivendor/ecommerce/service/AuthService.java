package com.mulivendor.ecommerce.service;

import com.mulivendor.ecommerce.request.SignupRequest;

public interface AuthService {
    String createUser(SignupRequest req);
}
