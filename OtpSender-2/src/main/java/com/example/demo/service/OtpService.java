package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OtpService {
    private final JavaMailSender emailSender;
    private final Map<String, String> otpStorage = new HashMap<>();
    private final Map<String, LocalDateTime> otpExpiryTimeStorage = new HashMap<>();
    private final Map<String, Integer> resendAttempts = new HashMap<>();  // Track resend attempts
    private static final int EXPIRATION_MINUTES = 1;
    private static final int MAX_RESEND_ATTEMPTS = 3; // Limit to 3 resends

    @Autowired
    public OtpService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public boolean sendOtp(String username, boolean isResend) {
        if (isResend) {
            // Check for resend limits only when this is a resend attempt
            int attempts = resendAttempts.getOrDefault(username, 0);
            if (attempts < MAX_RESEND_ATTEMPTS) {
                resendAttempts.put(username, attempts + 1);  // Increment resend attempts
            } else {
                return false; // Maximum resend attempts reached
            }
        }
        
        String otp = generateOtp();
        otpStorage.put(username, otp);
        otpExpiryTimeStorage.put(username, LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));

        sendEmail(username, otp);
        return true; // OTP sent successfully
    }

    public void resetResendAttempts(String username) {
        resendAttempts.put(username, 0); // Reset resend attempts after successful login or OTP generation
    }

    public void resetOtp(String username) {
        otpStorage.remove(username);
        otpExpiryTimeStorage.remove(username);
    }

    public boolean verifyOtp(String username, String otp) {
        if (otpStorage.containsKey(username)) {
            LocalDateTime expirationTime = otpExpiryTimeStorage.get(username);
            if (LocalDateTime.now().isBefore(expirationTime)) {
                if (otp.equals(otpStorage.get(username))) {
                    // OTP is correct, reset resend attempts
                    resetResendAttempts(username);
                    resetOtp(username);  // Clear OTP after successful validation
                    return true;
                }
            } else {
                resetOtp(username);  // Clear expired OTP
            }
        }
        return false;
    }

    private String generateOtp() {
        Random random = new Random();
        return String.valueOf(100000 + random.nextInt(900000));
    }

    private void sendEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp + ". It is valid for 2 minutes.");
        emailSender.send(message);
    }
}
