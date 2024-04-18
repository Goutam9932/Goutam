package com.springcore.collection;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Emp {
	private String name;
	private String address;
	private int id;
	@Override
	public String toString() {
		return "Emp [name=" + name + ", address=" + address + ", id=" + id + "]";
	}
	public Emp(String name, String address, int id) {
		super();
		this.name = name;
		this.address = address;
		this.id = id;
	}
	
	
	
}