package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void saveUser(User user, MultipartFile imageFile) throws IOException {
        if (!imageFile.isEmpty()) {
            user.setImage(imageFile.getBytes());
        }
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAdminDetails() {
        return userRepository.findByRole("ADMIN");
    }

    public List<User> searchUsersByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> 
                new IllegalArgumentException("Invalid user ID: " + id));
    }

    public void updateAdmin(User admin, MultipartFile imageFile) throws IOException {
        User existingAdmin = findUserById(admin.getId());

        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
            admin.setPassword(existingAdmin.getPassword());
        }

        if (!imageFile.isEmpty()) {
            admin.setImage(imageFile.getBytes());
        } else {
            admin.setImage(existingAdmin.getImage());
        }

        userRepository.save(admin);
    }

    public void updateUser(User user, MultipartFile imageFile) throws IOException {
        User existingUser = findUserById(user.getId());

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(existingUser.getPassword());
        }

        if (!imageFile.isEmpty()) {
            user.setImage(imageFile.getBytes());
        } else {
            user.setImage(existingUser.getImage());
        }

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
    }
}
