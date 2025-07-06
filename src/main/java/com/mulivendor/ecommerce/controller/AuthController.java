package com.mulivendor.ecommerce.controller;

import com.mulivendor.ecommerce.domain.USER_ROLE;
import com.mulivendor.ecommerce.request.SignupRequest;
import com.mulivendor.ecommerce.response.AuthResponse;
import com.mulivendor.ecommerce.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest request) {

        String jwt = authService.createUser(request);

        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setMessage("register success");
        response.setRole(USER_ROLE.ROLE_CUSTOMER);
        return ResponseEntity.ok(response);
    }
}
//start : 2:52:16
//2nd changes : 2:55:05 (RequestBody in createUserHandler)
//test : 3:00
//3:04:09 : all code work fine
//03:04:10 : Configure spring security and jwt token
//03:04:29 : config spring security
//03:06:00 : pom implement spring security