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
        if (otpService.verifyOtp(authentication.getName(), otp)) {
            // If OTP is correct, reset the resend attempts
            otpService.resetResendAttempts(authentication.getName());
            return "admin_dashboard"; // Redirect to dashboard if OTP is correct
        }
        return "redirect:/otp?error=true"; // Redirect back to OTP page with error
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
