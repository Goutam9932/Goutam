package com.springcore.standalone.collection;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Person {

	private List<String> friends;
	private Map<String, Integer>feestructor;
    private Properties properties;
	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	public Map<String, Integer> getFeestructor() {
		return feestructor;
	}

	public void setFeestructor(Map<String, Integer> feestructor) {
		this.feestructor = feestructor;
	}
	

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "Person [friends=" + friends + ", feestructor=" + feestructor + "]";
	}


	
	
	
}
