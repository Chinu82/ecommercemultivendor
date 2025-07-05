package com.mulivendor.ecommerce.controller;

import com.mulivendor.ecommerce.model.Users;
import com.mulivendor.ecommerce.request.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/signup")
    public ResponseEntity<Users> createUserHandler(@RequestBody SignupRequest request){
        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());

        return ResponseEntity.ok(user);
    }
}
//start : 2:52:16
//2nd changes : 2:55:05 (RequestBody in createUserHandler)
//test : 3:00