package delivery;

import java.util.HashMap;
import java.util.Map;

public class Order {
	Integer orderId;
	Map<Dish, Integer> dishes = new HashMap<>();
	String customerName;
	String restaurantName;
	int deliveryTime;
	int deliveryDistance;
	boolean delivered;
	
	public Order(String customerName, String restaurantName, int deliveryTime, int deliveryDistance, int orderId) {
		this.customerName = customerName;
		this.restaurantName = restaurantName;
		this.deliveryTime = deliveryTime;
		this.deliveryDistance = deliveryDistance;
		this.orderId=orderId;
		delivered = false;
	}

	public Integer getOrderId() {
		return orderId;
	}
	
	
	
	
}
