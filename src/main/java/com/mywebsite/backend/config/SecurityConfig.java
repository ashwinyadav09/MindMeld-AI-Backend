package com.mywebsite.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/users/register", "/api/users/login").permitAll() // Allow unauthenticated access
                .anyRequest().authenticated() // All other endpoints require authentication
            )
            .csrf().disable(); // Disable CSRF for simplicity (enable in production with frontend)
        return http.build();
    }
}