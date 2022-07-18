package fit;

import java.util.HashMap;
import java.util.Map;

public class Gym {
	String name;
	Map<String, Lesson> lessons = new HashMap<>();

	public Gym(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	void addLessons(String gymName, String speciality, int maxPart, String time, String... instructors) throws FitException {
		for (String t : time.split(",")) {
			if (!lessons.containsKey(t)) {
				Lesson l = new Lesson(gymName, speciality, maxPart, t, instructors);
				lessons.put(t, l);
				}
			else {throw new FitException();}
		}
		
	}
	
	void placeReservation(Customer c, int day, int slot) throws FitException {
		String time = day+"."+ slot;
		Lesson l = lessons.get(time);
		c.addLesson(l);
		l.addReservation(c);
	}

	public void addActualInstructor(String gymnname, int day, int slot, String instructor) throws FitException {
		String time = day+"."+ slot;
		Lesson l = lessons.get(time);
		if(l.isAPossibleIns(instructor)) {
			l.setActualInstructor(instructor);
		}
		else {
			throw new FitException();
		}
		
	}
	
	Integer numLesson() {
		return lessons.size();
	}
	
	
}
