package com.mulivendor.ecommerce.Ecommerce.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String email;
    private String fullName;
    private String otp;
}

//start 2:52:43