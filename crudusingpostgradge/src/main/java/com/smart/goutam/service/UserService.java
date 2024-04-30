package com.smart.goutam.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.smart.goutam.entity.User;

public interface UserService {

	ResponseEntity<User> createUser(User user);

	

	//ResponseEntity<User> createUser(User user, MultipartFile image);

	
}
