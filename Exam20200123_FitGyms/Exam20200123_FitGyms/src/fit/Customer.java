package fit;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	String name;
	int code;
	List<Lesson> lessons = new ArrayList<>();
	
	public Customer(String name, int code) {
		this.name = name;
		this.code = code;
	}
	
	void addLesson(Lesson l) {
		lessons.add(l);
	}
	
	
}
