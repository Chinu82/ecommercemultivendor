package com.mulivendor.ecommerce.controller;

import com.mulivendor.ecommerce.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping
    public ApiResponse HomeControllerHandler(){
        ApiResponse response = new ApiResponse();
        response.setMessage("Hello world");
        return response;
    }
}
