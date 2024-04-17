package com.smart.goutam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
	public class Employee {
	

	    @Id
	   
	    private Long id;

	    private String name;
	    private String status;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		@Override
		public String toString() {
			return "Employee [id=" + id + ", name=" + name + ", status=" + status + "]";
		}
		public Employee(Long id, String name, String status) {
			super();
			this.id = id;
			this.name = name;
			this.status = status;
		}
		public Employee() {
			// TODO Auto-generated constructor stub
		}
	    

}

