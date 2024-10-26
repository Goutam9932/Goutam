package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepository;
@Controller

public class UserController {

	
	 @Autowired
	    private UserRepository userRepository;
	 
	 @GetMapping("/register")
	    public String showRegistrationForm(Model model) {
	        model.addAttribute("user", new User());
	        return "register";  // return the register.html
	    }

	 @PostMapping("/register")
	    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
	        // Save the user data (e.g., using a repository if no service layer exists)
	        userRepository.save(user);

	        // Add a success message that will only show once on the redirected page
	        redirectAttributes.addFlashAttribute("successMessage", "User registered successfully!");

	        // Redirect to the registration page to clear the form fields
	        return "redirect:/register";
	    }

	    @GetMapping("/userdetails")
	    public String getAllUsers(Model model) {
	        List<User> users = userRepository.findAll();
	        model.addAttribute("users", users);  // Add user list to the model
	        return "userdetails";  // Return Thymeleaf page for user details
	    }

	    // Method to fetch admin details only
	    @GetMapping("/admindetails")
	    public String getAdminDetails(Model model) {
	        List<User> admins = userRepository.findByRole("ADMIN");  // Fetch only admin users
	        model.addAttribute("admins", admins);  // Add admin list to the model
	        return "admindetails";  // Return Thymeleaf page for admin details
	    }
	    // Search users by name
	    // Search users by name
	    @GetMapping("/search")
	    public String searchUsers(@RequestParam("name") String name, Model model) {
	        // Find users by name (case-insensitive search)
	        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
	        
	        if (!users.isEmpty()) {
	            User user = users.get(0); // Assuming we are only interested in the first match
	            
	            if ("admin".equalsIgnoreCase(user.getRole())) {
	                // If the user is an admin, show the admin details page
	                model.addAttribute("admins", List.of(user)); // Passing the admin details
	                return "singleuser";  // Redirect to a separate admin UI page (without edit)
	            } else {
	                // If the user is a regular user, allow editing
	                model.addAttribute("user", user);
	                return "singleuser";  // Redirect to a new UI page for individual user details and editing
	            }
	        } else {
	            model.addAttribute("error", "No user found with the name: " + name);
	            return "userdetails";  // Return to the same page if no user is found
	        }
	    }


	   
	    @GetMapping("/admin/edit")
	    public String showEditForm1(@RequestParam("id") Long id, Model model) {
	        // Find the user by ID
	        User user = userRepository.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));

	        // Only allow editing for users with the 'ADMIN' role
	        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
	            model.addAttribute("admin", user);  // Add the admin object to the model
	            return "admin-edit";  // Return the Thymeleaf template for editing admins
	        }

	        return "redirect:/admin/details";  // Redirect to admin details if the role is not ADMIN
	    }

	    @PostMapping("/admin/update")
	    public String saveAdmin(User admin) {
	        // Retrieve the current admin details from the database
	        User existingAdmin = userRepository.findById(admin.getId())
	                .orElseThrow(() -> new RuntimeException("Admin not found"));

	        // If the password is null or empty, keep the existing password
	        if (admin.getPassword() == null || admin.getPassword().isEmpty()) {
	            admin.setPassword(existingAdmin.getPassword());
	        }

	        userRepository.save(admin);  // Save the updated admin user
	        return "redirect:/userdetails";  // Redirect to the admin details page
	    }


	    @GetMapping("/user/edit")
	    public String showEditForm(@RequestParam("id") Long id, Model model) {
	        // Find the user by ID
	        User user = userRepository.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + id));

	        // Check if the user has an 'admin' role
	        if ("admin".equalsIgnoreCase(user.getRole())) {
	            // Redirect to the admin details page without an edit form
	            model.addAttribute("admins", List.of(user));
	            return "admindetails";
	        } else {
	            // Show the edit form for regular users
	            model.addAttribute("user", user);
	            return "user-edit";
	        }
	    }


	    @PostMapping("/user/edit")
	    public String editUser(@ModelAttribute User user, Model model) {
	        // Fetch the existing user from the database
	        User existingUser = userRepository.findById(user.getId())
	                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + user.getId()));

	        // Preserve the existing password if the password is not provided in the form
	        if (user.getPassword() == null || user.getPassword().isEmpty()) {
	            user.setPassword(existingUser.getPassword());
	        }

	        // Save the updated user data
	        userRepository.save(user);
	        return "redirect:/userdetails"; // Redirect to the user details page after updating
	    }

	    
	    @PostMapping("/user/delete/{id}")
	    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
	        if (userRepository.existsById(id)) {
	            userRepository.deleteById(id);
	            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
	        } else {
	            redirectAttributes.addFlashAttribute("errorMessage", "User not found with ID: " + id);
	        }
	        return "redirect:/userdetails";
	    }

  
}