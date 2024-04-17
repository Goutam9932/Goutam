package com.smart.goutam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.smart.goutam.dao.EmployeeRepository;
import com.smart.goutam.entity.Employee;

@Controller
public class EmployeeHrController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/employee-details")
	public String showEmployeeDetailsPage(Model model) {

		return "employee-details"; // This corresponds to the name of your HTML file
	}

	@GetMapping("/employeeHr")
	public String showEmployeeDetailsPage1(Model model) {
		List<Employee> employees = employeeRepository.findAll(); // Fetch all employees from the database

		return "Hr"; // This corresponds to the name of your HR HTML file
	}

	@MessageMapping("/submit")
	@SendTo("/topic/employee")
	public Employee submitEmployeeDetails(Employee employee) {
		employee.setStatus("Pending");
		return employeeRepository.save(employee);
	}

	@MessageMapping("/approve/{id}")
	@SendTo("/topic/employee-status/") // Send status update to the employee topic

	public Employee approveEmployee(@ModelAttribute Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
		employee.setStatus("Approved");
		return employeeRepository.save(employee);
	}

	@MessageMapping("/reject/{id}")
	@SendTo("/topic/employee-status/") // Send status update to the employee topic

	public Employee rejectEmployee(@ModelAttribute Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
		employee.setStatus("Rejected");
		return employeeRepository.save(employee);
	}
}
