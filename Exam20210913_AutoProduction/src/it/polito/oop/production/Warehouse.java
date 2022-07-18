package it.polito.oop.production;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
	String name;
	int capacity;
	
	public Warehouse(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
	}
	
	Map<Model, Integer> parked = new HashMap<>();
	
	int getNumbParkedCar() {
		return parked.values().stream().mapToInt(p -> p).sum();
	}
	
	boolean isFull() {
		return this.getNumbParkedCar() >= capacity;
	}
	

}
