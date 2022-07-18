package trail;

import java.util.ArrayList;
import java.util.List;

public class Delegate {
	String name;
	String surname;
	String cf;
	List<Location> locationsAssigned = new ArrayList<>();
	
	public Delegate(String name, String surname, String cf) {
		this.name = name;
		this.surname = surname;
		this.cf = cf;
	}

	@Override
	public String toString() {
		return surname + ", " + name + ", " + cf;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getCf() {
		return cf;
	}
	
	void assignLocation(Location l){
		locationsAssigned.add(l);
	}
	
	
	
}
