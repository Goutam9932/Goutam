package com.springcore.ci;

public class Person {
	
	private String name;
	private int personid;
	private Adress adress;
	
	
	
	public Person(String name, int personid,Adress adress) {
		this.name=name;
		this.personid=personid;
		this.adress=adress;
		
	}


	@Override
	public String toString() {
		return this.name+" : "+this.personid+":"+this.adress; 
	}

	
}
