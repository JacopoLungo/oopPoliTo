package example;
import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

import delivery.*;

public class TestApp {
@Test
public void test() throws DeliveryException {
	
	Delivery d = new Delivery();
	//R1
	
    d.addCategory("Traditional");
    d.addCategory("Fast Food");
    d.addCategory("Chinese");
	try{
		d.addCategory("Chinese");
	    fail("Duplicated category not detected");
	} catch(Exception ex){} //ok

	List<String> c = d.getCategories();
	assertEquals(3,c.size());
	
    d.addRestaurant("Sol levante", "Chinese");
    d.addRestaurant("La trattoria", "Traditional");
	try {
		d.addRestaurant("Japps", "Japanese");
	    fail("Unknown category not detected");
	} catch(Exception ex){} //ok
	
	assertEquals(1,d.getRestaurantsForCategory("Chinese").size());
	
	//R2
    d.addDish("Involtini primavera", "Sol levante", (float) 10.1);
    d.addDish("Ravioli", "Sol levante", (float) 20.1);
    d.addDish("sughetto", "La trattoria", (float) 10.5);
	try {
		d.addDish("Involtini primavera", "Sol levante", (float) 0.1);
	    fail("Duplicated dish not detected");
	} catch(Exception ex){} //ok
	
	assertEquals(2,d.getDishesForRestaurant("Sol levante").size());
	
//	//R3
	String[] dishNames = { "Involtini primavera" , "Ravioli" };
	int[] quantities1 = { 2 , 3 };
	int[] quantities2 = { 3 , 5 };
	int o1 = d.addOrder(dishNames, quantities1, "Customer1", "Sol levante", 10, 3);
	int o2 = d.addOrder(dishNames, quantities2, "Customer2", "Sol levante", 10, 4);
	String[] dishNames2 = {"sughetto"};
	int[] quantities3 = { 2 };
	int o3 = d.addOrder(dishNames2, quantities3, "Customer3", "La trattoria", 10, 4);
	assertEquals(1,o1);
	assertEquals(2,o2);
	assertEquals(2,d.scheduleDelivery(10, 5, 2).size());
	
	//R4
	d.setRatingForRestaurant("Sol levante", 5);
	d.setRatingForRestaurant("Sol levante", 3);
	d.setRatingForRestaurant("La trattoria", 3);
	assertEquals("Sol levante",d.restaurantsAverageRating().get(0));
	
	//R5
	assertEquals(new Long(2),d.ordersPerCategory().get("Chinese"));
	System.out.println(d.bestRestaurant());
	System.out.println(d.ordersPerCategory().toString());
	
}
}
