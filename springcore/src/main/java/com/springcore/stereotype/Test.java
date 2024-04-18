package com.springcore.stereotype;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		
		
		ApplicationContext con=
				new ClassPathXmlApplicationContext("com/springcore/stereotype/steroconfig.xml");
		Student student=con.getBean("ob", Student.class);
//		System.out.println(student);
//		System.out.println();
//		System.out.println(student.getAddress().getClass().getName());
//		
		//System.out.println(student.hashCode());
		Student student2=con.getBean("ob", Student.class);
		//System.out.println(student2.hashCode());
		Teacher t1=con.getBean("teacher",Teacher.class);
		Teacher t2=con.getBean("teacher",Teacher.class);
		//object create korer pore hashcode guoke print kora hlo
		System.out.println(t1.hashCode());
		System.out.println(t2.hashCode());
	}

}
