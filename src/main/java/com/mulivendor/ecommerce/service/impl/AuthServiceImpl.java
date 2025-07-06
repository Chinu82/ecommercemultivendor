package com.mulivendor.ecommerce.service.impl;

import com.mulivendor.ecommerce.config.JwtProvider;
import com.mulivendor.ecommerce.domain.USER_ROLE;
import com.mulivendor.ecommerce.model.Cart;
import com.mulivendor.ecommerce.model.Users;
import com.mulivendor.ecommerce.model.VerificationCode;
import com.mulivendor.ecommerce.repository.CartRepository;
import com.mulivendor.ecommerce.repository.UserRepository;
import com.mulivendor.ecommerce.repository.VerificationCodeRepository;
import com.mulivendor.ecommerce.request.LoginRequest;
import com.mulivendor.ecommerce.request.SignupRequest;
import com.mulivendor.ecommerce.response.AuthResponse;
import com.mulivendor.ecommerce.service.AuthService;
import com.mulivendor.ecommerce.service.EmailService;
import com.mulivendor.ecommerce.util.OtpUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider jwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final CustomUserServiceImpl customUserService;
    //Constructor injection
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CartRepository cartRepository, JwtProvider jwtProvider, VerificationCodeRepository verificationCodeRepository, EmailService emailService, CustomUserServiceImpl customUserService){
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.verificationCodeRepository = verificationCodeRepository;
        this.emailService = emailService;
        this.customUserService = customUserService;
    }

    //OtpImpl


    @Override
    public void sentLoginSignupOtp(String email) throws Exception {
        String SIGNING_PREFIX = "signing_";

        if (email.startsWith(SIGNING_PREFIX)){
            email = email.substring(SIGNING_PREFIX.length());

            Users user = userRepository.findByEmail(email);

            if (user == null){
                throw new Exception("user not exist with provided email");
            }
        }
        VerificationCode isExist = verificationCodeRepository.findByEmail(email);
        if (isExist != null){
            verificationCodeRepository.delete(isExist);
        }

        //if not exist then create new
        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        //send to users
        String subject = "E-Commerce Multi-vendor otp for Login/Signup";
        String text = "Your Login/Signup Otp is : ";

        emailService.sendVerificationOtpEmail(email,otp,subject,text);
    }

    @Override
    public String createUser(SignupRequest req) throws Exception {

        //Verify for otp
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());
        if (verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())){
            throw new Exception("wrong otp...");
        }



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

    @Override
    public AuthResponse signing(LoginRequest request) {
        String username = request.getEmail();
        String otp = request.getOtp();

        Authentication authentication = authenticate(username, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generatedToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login success");

        //Granted authorities
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;
    }

    private Authentication authenticate(String username, String otp) {
        UserDetails userDetails = customUserService.loadUserByUsername(username);

        if (username == null){
            throw new BadCredentialsException("Invalid username or, password");
        }

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

        if (verificationCode == null || verificationCode.getOtp().equals(otp)){
            throw new BadCredentialsException("wrong otp");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

}
//Fnished of Authservice impl SecurityContextHolder : 4:02:45
//Exception getting : 04:09:10
//for verification code : 04:13:00
//for otp : 04:49:00
//Done : 05:00:00
//Waiting for testing :