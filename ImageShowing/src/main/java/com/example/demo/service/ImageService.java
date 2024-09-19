package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Image;
import com.example.demo.repo.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image saveImage(byte[] data) {
        Image image = new Image();
        image.setData(data);
        return imageRepository.save(image);
    }
    public Image getImageDataById(Long id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        
        // Handle case where image is not found
        if (imageOptional.isPresent()) {
            return imageOptional.get();
        } else {
            throw new RuntimeException("Image not found with id: " + id);
        }
    }
}
