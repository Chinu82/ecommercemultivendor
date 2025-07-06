package com.mulivendor.ecommerce.service.impl;

import com.mulivendor.ecommerce.domain.USER_ROLE;
import com.mulivendor.ecommerce.model.Seller;
import com.mulivendor.ecommerce.model.Users;
import com.mulivendor.ecommerce.repository.SellerRepository;
import com.mulivendor.ecommerce.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private static final String SELLER_PREFIX = "seller_";

    public CustomUserServiceImpl(UserRepository userRepository, SellerRepository sellerRepository){
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.startsWith(SELLER_PREFIX)){
            String actualUserName = username.substring(SELLER_PREFIX.length());
            Seller seller = sellerRepository.findByEmail(actualUserName);

            if (seller != null){
                return buildUserDetails(seller.getEmail(), seller.getPassword(), seller.getRole());
            }
        }
        else{
            Users user = userRepository.findByEmail(username);
            if (user != null){
                return buildUserDetails(user.getEmail(),user.getPassword(), user.getRole());
            }
        }
        throw new UsernameNotFoundException("user or seller not found with email - "+username);
    }

    private UserDetails buildUserDetails(String email, String password, USER_ROLE role) {
        if (role == null){
            role = USER_ROLE.ROLE_CUSTOMER;
        }

        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+role));

        return new User(email, password, authorityList);
    }
}
//this has done in 1st part : 04:22:30 works correctly