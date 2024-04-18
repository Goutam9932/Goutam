package com.springcore.lifecycle;

public class sam {
	
	private double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		System.out.println("Setting price");
		this.price = price;
	}

	public sam() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "sam [price=" + price + "]";
	}
	
	public void init() {
		System.out.println(" Inside init method");
	}
	public void destroy() {
		System.out.println("Inside destroy method");
	}

}
