package com.mulivendor.ecommerce.service.impl;

import com.mulivendor.ecommerce.config.JwtProvider;
import com.mulivendor.ecommerce.domain.USER_ROLE;
import com.mulivendor.ecommerce.model.Cart;
import com.mulivendor.ecommerce.model.Users;
import com.mulivendor.ecommerce.repository.CartRepository;
import com.mulivendor.ecommerce.repository.UserRepository;
import com.mulivendor.ecommerce.request.SignupRequest;
import com.mulivendor.ecommerce.service.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;

    //Constructor injection
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CartRepository cartRepository, JwtProvider jwtProvider){
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String createUser(SignupRequest req) {

        Users user = userRepository.findByEmail(req.getEmail());
        if(user == null) {
            Users createUser = new Users();
            createUser.setEmail(req.getEmail());
            createUser.setFullName(req.getFullName());
            createUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createUser.setMobile("9320498203928");
            createUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(createUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));


        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generatedToken(authentication);
    }

}
//Fnished of Authservice impl SecurityContextHolder : 4:02:45
//Exception getting : 04:09:10