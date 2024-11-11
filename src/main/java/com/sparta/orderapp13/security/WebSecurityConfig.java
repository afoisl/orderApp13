package com.sparta.orderapp13.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login", "/categories", "/stores", "/food/**", "/review/**").permitAll()
                        .requestMatchers("/user/**").hasAnyRole("CUSTOMER", "MASTER")
                        .requestMatchers("/order/**").hasAnyRole("CUSTOMER", "OWNER")
                        .requestMatchers("/categories/**", "/stores/**", "/food/**").hasAnyRole("MANAGER", "MASTER", "OWNER")
                        .requestMatchers("/payment/**").hasAnyRole("CUSTOMER", "OWNER", "MASTER")
                        .requestMatchers("/ai").hasAnyRole("MASTER", "OWNER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );

        return http.build();
    }
}
