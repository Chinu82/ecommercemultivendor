package com.mulivendor.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/*/product/reviews").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    //Required by Spring Security to hash passwords when registering or validating users.
    //BCryptPasswordEncoder is the best and most secure choice
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS configuration
    //Enables Cross-Origin Resource Sharing (CORS) so your frontend (e.g., React) can talk to this backend.
    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration cfg = new CorsConfiguration();
            cfg.setAllowedOrigins(Collections.singletonList("*"));
            cfg.setAllowedMethods(Collections.singletonList("*"));
            cfg.setAllowedHeaders(Collections.singletonList("*"));
            cfg.setAllowCredentials(true);
            cfg.setExposedHeaders(Collections.singletonList("*"));
            cfg.setMaxAge(3600L);
            return cfg;
        };
    }

    // For calling external REST APIs
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}


//Start wiith security config : 03:08:25 (Security filter chain)
//Make it updated with spring security 6.0 : 03:13:00
//Done with cors config : 03:20:50
//Implement for jwt : 03:24:00  (pom.xml - jwt - api/token)