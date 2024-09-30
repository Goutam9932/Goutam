package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private final Map<String, String> otpStorage = new HashMap<>(); // Store OTPs in memory for simplicity

    private final JavaMailSender emailSender; // To send emails

    @Autowired
    public OtpService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    // Generate and send OTP to the user's email
    public void sendOtp(String username) {
        String otp = generateOtp(); // Generate OTP
        otpStorage.put(username, otp); // Store OTP against the username

        // Send OTP to user's email
        sendEmail(username, otp);
    }

    // Verify the entered OTP
    public boolean verifyOtp(String username, String otp) {
        return otp.equals(otpStorage.get(username)); // Verify the OTP
    }

    // Generate a 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }

    private void sendEmail(String to, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otp);
            emailSender.send(message); // Send the email
            System.out.println("OTP sent to: " + to); // Log success
        } catch (Exception e) {
            e.printStackTrace(); // Log error
        }
    }

}
