package com.springcore.ci;



public class Adress {
	
	private String city;
	private String name;
	private int pin;
	private Emp employee;
	public Adress(String city, String name, int pin, Emp employee) {
		super();
		this.city = city;
		this.name = name;
		this.pin = pin;
		this.employee = employee;
	}
	@Override
	public String toString() {
		return "Adress [city=" + city + ", name=" + name + ", pin=" + pin + ", employee=" + employee + "]";
	}
	
	
	

}
