package fit;

import java.util.*;
import java.util.stream.Collectors;


public class Fit {
	Map<String, Gym> gymsMap = new HashMap<>();
	Map<Integer, Customer> custMap = new HashMap<>();
	int custNumb = 0;

    public static int MONDAY    = 1;
    public static int TUESDAY   = 2;
    public static int WEDNESDAY = 3;
    public static int THURSDAY  = 4;
    public static int FRIDAY    = 5;
    public static int SATURDAY  = 6;
    public static int SUNDAY    = 7;
    
	public Fit() {
	
	}
	// R1 
	
	public void addGymn (String name) throws FitException {
		if(gymsMap.containsKey(name)) {throw new FitException();}
		Gym gym = new Gym(name);
		gymsMap.put(gym.getName(), gym);
	}
	
	public int getNumGymns() {
		return gymsMap.values().size();
	}
	
	//R2

	public void addLessons (String gymnname, 
	                        String activity, 
	                        int maxattendees, 
	                        String slots, 
	                        String ...allowedinstructors) throws FitException{
		if(!gymsMap.containsKey(gymnname)) {throw new FitException();}
		for (String s : slots.split(",")) {
			int day = Integer.parseInt(s.split("\\.")[0]);
			int hour = Integer.parseInt(s.split("\\.")[1]);
			if (!(day >= 1 && day <= 7)) {throw new FitException();}
			if (!(hour >= 8 && day <= 20)) {throw new FitException();}
		}
		
		gymsMap.get(gymnname).addLessons(gymnname, activity, maxattendees, slots, allowedinstructors);

	}
	
	//R3
	public int addCustomer(String name) {
		custNumb++;
		Customer c = new Customer(name, custNumb);
		custMap.put(c.code, c);
		return c.code;
	}
	
	public String getCustomer (int customerid) throws FitException{
		if(!custMap.containsKey(customerid)) {throw new FitException();}
	    return custMap.get(customerid).name;
	}
	
	//R4
	
	public void placeReservation(int customerid, String gymnname, int day, int slot) throws FitException{
		if(!custMap.containsKey(customerid)) {throw new FitException();}
		if(!gymsMap.containsKey(gymnname)) {throw new FitException();}
		if (!(day >= 1 && day <= 7)) {throw new FitException();}
		if (!(slot >= 8 && slot <= 20)) {throw new FitException();}
		Customer c = custMap.get(customerid);	
		gymsMap.get(gymnname).placeReservation(c, day, slot);
	}
	
	public int getNumLessons(int customerid) {
		return custMap.get(customerid).lessons.size();
	}
	
	
	//R5
	
	public void addLessonGiven (String gymnname, int day, int slot, String instructor) throws FitException{
		if(!gymsMap.containsKey(gymnname)) {throw new FitException();}
		if (!(day >= 1 && day <= 7)) {throw new FitException();}
		if (!(slot >= 8 && slot <= 20)) {throw new FitException();}
		gymsMap.get(gymnname).addActualInstructor(gymnname, day, slot, instructor);
	}
	
	public int getNumLessonsGiven (String gymnname, String instructor) throws FitException {
		if(!gymsMap.containsKey(gymnname)) {throw new FitException();}
		return gymsMap.get(gymnname).lessons.values().stream()
				.filter(l -> {
					if(l.getActualInstructor() != null) {
						return l.getActualInstructor().equals(instructor);
						}
					else {return false;}
					})
				.collect(Collectors.counting()).intValue();
	    
	}
	//R6
	
	public String mostActiveGymn() {
		int max = gymsMap.values().stream().mapToInt(Gym::numLesson).max().orElse(0);
		return gymsMap.values().stream().filter(l -> l.numLesson() == max)
				.collect(Collectors.toList()).get(0).getName();
	}
	
	public Map<String, Integer> totalLessonsPerGymn() {		
		return gymsMap.values().stream().collect(Collectors.toMap(Gym::getName,
												Gym::numLesson));
	}
	
	public SortedMap<Integer, List<String>> slotsPerNofParticipants(String gymnname) throws FitException{
	    return gymsMap.get(gymnname).lessons.values().stream()
	    		.collect(Collectors.groupingBy(Lesson::numReservations,
	    										TreeMap::new,
	    										Collectors.mapping(l -> l.time,
	    												Collectors.toList())));
	}
	

	
	
	
	


}
