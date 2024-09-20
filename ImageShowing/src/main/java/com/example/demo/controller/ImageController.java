package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Image;
import com.example.demo.repo.ImageRepository;
import com.example.demo.service.ImageService;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    // Endpoint to show the upload form
    @GetMapping("/")
    public String uploadForm() {
        return "upload-form"; // Thymeleaf template for image upload
    }

    // Endpoint to upload the image and handle the form submission
    @PostMapping("/upload")
    public String uploadImage(MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select an image to upload.");
            return "upload-form";
        }

        try {
            byte[] data = file.getBytes();
            imageService.saveImage(data); // Save image to database
            model.addAttribute("message", "Image uploaded successfully!");
        } catch (IOException e) {
            model.addAttribute("message", "Error uploading image: " + e.getMessage());
        }

        return "upload-form"; // Reload form after upload
    }

    // Endpoint to display all uploaded images in a list
    @GetMapping("/images")
    public String viewImages(Model model) {
        List<Image> images = imageRepository.findAll(); // Fetch all images
        model.addAttribute("images", images); // Add images to the model
        return "viewImages"; // Thymeleaf template for displaying images
    }

    // Endpoint to view the actual image (by ID) in another page or tab
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Image image = imageService.getImageDataById(id);  // Fetch image from DB
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)  // Or whatever the actual type is
                .body(image.getData());  // Assuming 'data' is a byte[] containing the image
    }

    @GetMapping("/image1/{id}")
    public ResponseEntity<byte[]> getImage1(@PathVariable("id") Long id) {
        Image image = imageService.getImageDataById(id);
        if (image != null && image.getData() != null) {
            System.out.println("Returning image with ID: " + id);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)  // Adjust based on the image type
                    .body(image.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // Endpoint to open a new tab and display selected images dynamically
    @GetMapping("/images/view")
    public String viewImagesPage(Model model) {
        List<Image> images = imageRepository.findAll(); // Fetch all images for display
        model.addAttribute("images", images); // Add them to the model
        return "new"; // Thymeleaf template for image display in new tab
    }
}
