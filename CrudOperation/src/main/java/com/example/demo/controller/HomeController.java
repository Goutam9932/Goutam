package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;

@RestController
public class HomeController {
	
	
	@Autowired
	 private StudentRepository studentRepository;

	@GetMapping("/")
	public String index() {
		return "welcome to spring boot crud operation";
	}

	@PostMapping("/saveStudent")
	public Student saveData(@RequestBody Student student) {
		
		return studentRepository.save(student);
	

	}

	@GetMapping("/getAllStudent")
	public List<Student> getAll() {
		List<Student> studentList = studentRepository.findAll();
		return studentList;
	}

	@DeleteMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable String id) {
		Student student = studentRepository.findById(id).get();
		if (student != null)
			studentRepository.delete(student);
		return "Delete successfully";

	}
	 @PutMapping("/updateStudent/{id}")
	    public String updateStudent(@PathVariable String id, @RequestBody Student updatedStudent) {
	        // Find the student by ID
	        Student existingStudent = studentRepository.findById(id).orElse(null);

	        // Check if the student exists
	        if (existingStudent == null) {
	            return "Student not found";
	        }
	     // Update the fields of the existing student with the new data
	        existingStudent.setName(updatedStudent.getName());
	        
	        studentRepository.save(existingStudent);
	        return "Update successfully";
	    }

}
