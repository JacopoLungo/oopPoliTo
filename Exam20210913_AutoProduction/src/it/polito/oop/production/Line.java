package it.polito.oop.production;

import java.util.ArrayList;
import java.util.List;

public class Line {
	public String name; //key
	Integer capacity;
	Integer engineType;
	public List<Model> modelsToDo= new ArrayList<>(); //lista che contiene un entrata per ogni unità del modello in lavorazione
	
	public Line(String name, int capacity, int engineType) {
		this.name = name;
		this.capacity = capacity;
		this.engineType = engineType;
	}
	
	boolean isFull() {
		if (capacity.equals(modelsToDo.size())) {
			return true;
		}
		return false;
	}
	
	int getCapacity() {
		return capacity;
	}
	
	int getAllocatedCapacity() {
		return modelsToDo.size();
	}

}
