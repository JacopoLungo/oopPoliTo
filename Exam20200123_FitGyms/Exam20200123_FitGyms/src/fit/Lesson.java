package fit;

import java.util.ArrayList;
import java.util.List;

public class Lesson {
	String gymName;
	String speciality;
	int maxPart;
	String time;
	String actualInstructor;
	List<String> possibleInstructors = new ArrayList<>();
	List<Customer> reservations = new ArrayList<>();
	
	public Lesson(String gymName, String speciality, int maxPart, String time, String... instructors) {
		this.gymName = gymName;
		this.speciality = speciality;
		this.maxPart = maxPart;
		this.time = time;
		
		for(String inst : instructors) {
			this.possibleInstructors.add(inst);
		}
	}
	
	void addReservation(Customer c) throws FitException {
		if(reservations.contains(c)) {throw new FitException();}
		if(reservations.size() >= maxPart) {throw new FitException();}

		reservations.add(c);
	}

	public String getActualInstructor() {
		return actualInstructor;
	}

	public void setActualInstructor(String actualInstructor) {
		this.actualInstructor = actualInstructor;
	}
	
	boolean isAPossibleIns(String inst) {
		return possibleInstructors.contains(inst);
	}
	
	Integer numReservations() {
		return reservations.size();
	}
	
}
