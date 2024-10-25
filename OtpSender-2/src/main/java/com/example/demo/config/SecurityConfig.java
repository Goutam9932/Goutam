package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.demo.service.OtpService;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Autowired
    private OtpService otpService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();  // Plain text password for now
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .requestMatchers("/login", "/register").permitAll()
                .requestMatchers("/admin/**").authenticated()
            .and()
                .formLogin()
                    .loginPage("/login")
                    .failureHandler((request, response, exception) -> {
                        response.sendRedirect("/login?error=true");  // Redirect to error page on failure
                    })
                    .successHandler((request, response, authentication) -> {
                        String email = authentication.getName();

                        // Send OTP without counting as resend
                        otpService.sendOtp(email, false);  // Send initial OTP, no resend
                        response.sendRedirect("/otp");
                    })
                    .permitAll()
            .and()
                .logout()
                    .permitAll();
        return http.build();
    }
}
