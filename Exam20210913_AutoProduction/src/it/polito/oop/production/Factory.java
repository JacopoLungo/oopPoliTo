package it.polito.oop.production;

import java.util.HashMap;
import java.util.Map;

public class Factory {
	String name; //key
	public Map<String, Line> lines = new HashMap<>();

	public Factory(String name) {
		this.name = name;
	}
	
	int getLineCapacity(String lineName) {
		return lines.get(lineName).getCapacity();
	}
	
	int getLineAllocatedCapacity(String lineName) {
		return lines.get(lineName).getAllocatedCapacity();
	}

}
