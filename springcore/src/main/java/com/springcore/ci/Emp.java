package com.springcore.ci;

public class Emp {
	
	private String name;
	private String address;
	private int id;
	public Emp(String name, String address, int id) {
		super();
		this.name = name;
		this.address = address;
		this.id = id;
	}
	@Override
	public String toString() {
		return "Emp [name=" + name + ", address=" + address + ", id=" + id + "]";
	}
	

}
