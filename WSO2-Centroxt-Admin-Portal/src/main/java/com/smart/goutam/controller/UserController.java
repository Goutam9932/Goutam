package com.smart.goutam.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.smart.goutam.entity.Group;
import com.smart.goutam.entity.User;
import com.smart.goutam.service.GroupService;
import com.smart.goutam.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	private final UserService userService;
	private final GroupService groupService;
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserController(UserService userService, GroupService groupService, BCryptPasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.groupService = groupService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(HttpServletRequest request) {
	    String userName = request.getHeader("Authorization");
	    // Decode the Authorization header to extract the username and password
	    String[] credentials = new String(Base64.getDecoder()
	            .decode(userName.split(" ")[1])).split(":");
	    String username = credentials[0];
	    String password = credentials[1];

	    // Retrieve user by email
	    User user = userService.findByEmail(username);

	    // Check if user exists and password matches
	    if (user != null && passwordEncoder.matches(password, user.getPassword())) {
	        // Check if the user is an admin
	        if (user.getRole() == User.Role.ADMIN) {
	            // Return message for admin login
	            return ResponseEntity.ok().body("Admin login successful");
	        } else {
	            // Return user details for regular user login
	            return ResponseEntity.ok().body(user);
	        }
	    } else {
	        // Return unauthorized if login fails
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	}





	@PostMapping(value = "/newuser", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> addUser(@RequestParam("userName") String userName,
	                                      @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
	                                      @RequestParam("country") String country, @RequestParam("email") String email,
	                                      @RequestParam("mobile") String mobile, @RequestParam("password") String password,
	                                      @RequestParam("role") String role, @RequestParam("groupId") Long groupId,
	                                      @RequestParam("file") MultipartFile file) {
	    // Check if the logged-in user has the 'ADMIN' role
	    if (!isAdmin()) {
	        // If not an admin, return a 403 Forbidden response with a custom message
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to perform this action");
	    }

	    // Handle file upload here
	    if (file.isEmpty()) {
	        // Handle case where file is empty
	        return ResponseEntity.badRequest().body("File is empty");
	    }

	    try {
	        // Create a new User object
	        User user = new User();
	        user.setUserName(userName);
	        user.setFirstName(firstName);
	        user.setLastName(lastName);
	        user.setCountry(country);
	        user.setEmail(email);
	        user.setMobile(mobile);
	        user.setPassword(password);

	        // Set role
	        user.setRole(User.Role.valueOf(role)); // Assuming role is provided as a string

	        // Set group
	        Group group = groupService.getGroupById(groupId); // Assuming you have a method to get group by ID
	        if (group == null) {
	            // Handle case where group is not found
	            return ResponseEntity.notFound().build();
	        }
	        user.setGroup(group);

	        // Set image data
	        user.setImage(file.getBytes());

	        // Call the service method to save the user
	        ResponseEntity<User> responseEntity = userService.createUser(user);

	        // Check if user creation is successful
	        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
	            // Return the entire user object in the response
	            return ResponseEntity.ok().body(responseEntity.getBody());
	        } else {
	            // Handle the case where user creation fails
	            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle file upload error
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
	    }
	}

	// Method to check if the logged-in user has the 'ADMIN' role
	private boolean isAdmin() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authentication.getAuthorities().stream()
	            .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
	}

	@PostMapping("/newGroup")
	public ResponseEntity<Group> addGroup(@RequestBody Group group) {
		return groupService.createGroup(group);
	}
}
