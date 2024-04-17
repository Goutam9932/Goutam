package com.smart.goutam.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.goutam.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	

}