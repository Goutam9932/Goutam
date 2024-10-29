package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; // Correct import
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.OtpService;

@Controller
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @GetMapping
    public ModelAndView showOtpPage(Authentication authentication) {
        // Reset the resend attempts when user comes back to OTP page after login
        otpService.resetResendAttempts(authentication.getName());
        return new ModelAndView("otp"); // Return the OTP view
    }

    @PostMapping
    public String verifyOtp(@RequestParam String otp, Authentication authentication) {
        String email = authentication.getName();

        // Verify OTP
        if (otpService.verifyOtp(email, otp)) {
            otpService.resetResendAttempts(email);

            // Redirect based on user roles
            if (authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin/dashboard";
            } else if (authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
                return "userdetails1"; // Make sure this endpoint is available
            }
        }

        // Redirect back to OTP page with error if OTP verification fails
        return "redirect:/otp?error=true";
    }

    @PostMapping("/send")
    public ResponseEntity<String> resendOtp(Authentication authentication) {
        boolean otpSent = otpService.sendOtp(authentication.getName(), true);
        if (otpSent) {
            return ResponseEntity.ok("OTP resent successfully");
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                                 .body("OTP limit reached");
        }
    }
}
