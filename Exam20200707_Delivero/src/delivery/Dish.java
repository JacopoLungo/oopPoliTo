package delivery;

import java.util.ArrayList;
import java.util.List;

public class Dish {
	String name; //univoco per un ristorante
	float price;
	List<Restaurant> restaurants = new ArrayList<>(); //ristoranti in cui è servito
	
	public Dish(String name, float price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}
	
	
	
}
