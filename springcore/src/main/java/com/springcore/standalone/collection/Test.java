package com.springcore.standalone.collection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

	public static void main(String[] args) {

		ApplicationContext con = new ClassPathXmlApplicationContext(
				"com/springcore/standalone/collection/aloneconfig.xml");
		Person person1 = con.getBean("person1", Person.class); // variable e store kora hlo
		System.out.println(person1);
		System.out.println(person1.getFriends().getClass().getName());
		System.out.println("--------------------------------------");
		System.out.println(person1.getFeestructor());
		System.out.println("--------------------------------------");
		System.out.println(person1.getProperties());
	}

}
