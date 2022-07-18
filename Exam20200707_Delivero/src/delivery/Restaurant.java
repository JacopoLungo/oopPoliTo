package delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
	String name; //nome univoco
	String category;
	Map<String, Dish> dishes = new HashMap<>();
	Map<Integer, Order> orders = new HashMap<>();
	List<Integer> ratings = new ArrayList<>();
	
	public Restaurant(String name, String category) {
		this.name = name;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}
	
	public void addDish(Dish d) {
		dishes.put(d.name, d);
	}
	
	float getAverage() {
		if (ratings.size() == 0) {
			return -1;
		}
		int votiTot = ratings.size();
		int sumVoti = ratings.stream().mapToInt(v -> v).sum();
		return (float) sumVoti/votiTot; 
	}
	
}
