package com.example.demo.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class EmployeeidGenerator implements IdentifierGenerator {
	private static final String PREFIX = "CEN";

    private static int counter = 1;

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		// TODO Auto-generated method stub
		 String formattedCounter = String.format("%03d", ++counter);
	        return PREFIX + formattedCounter;
	}
	
	
}

	
	


