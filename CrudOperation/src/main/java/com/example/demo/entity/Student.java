package com.example.demo.entity;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@GenericGenerator(
    name = "custom-sequence",
    strategy = "com.example.demo.generator.EmployeeidGenerator"
)
public class Student {
	    @Id
	    @GeneratedValue(generator = "custom-sequence", strategy = GenerationType.AUTO)
	   
	  private String employeeId;
      private String name;
	  private String address;
	  private int age;
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Student(String employeeId, String name, String address, int age) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.address = address;
		this.age = age;
	}

	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		address = address;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Student [employeeId=" + employeeId + ", name=" + name + ", Address=" + address + ", age=" + age + "]";
	}
	
	
}