package trail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Trail {
	Map<String, Location> locations = new HashMap<>();
	Map<Integer, Runner> runners = new HashMap<>();
	Map<String, Delegate> delegates = new HashMap<>();
	int totRunners = 0;
	int totLocations = 0;
	
    public int newRunner(String name, String surname){
    	totRunners ++;
    	Runner r = new Runner(totRunners, name, surname);
    	runners.put(r.getBibNumber(), r);
        return totRunners;
    }
    
    public Runner getRunner(int bibNumber){
        return runners.get(bibNumber);
    }
    
    public Collection<Runner> getRunner(String surname){
        return runners.values().stream().filter(r -> r.getSurname().equals(surname))
				        				.sorted(Comparator.comparing(Runner::getBibNumber))
				        				.collect(Collectors.toList());
    }
    
    public List<Runner> getRunners(){
        return runners.values().stream()
				.sorted(Comparator.comparing(Runner::getBibNumber))
				.collect(Collectors.toList());
    }

    public List<Runner> getRunnersByName(){
        return runners.values().stream()
				.sorted(Comparator.comparing(Runner::getSurname)
						.thenComparing(Runner::getName)
//						.thenComparing(Runner::getBibNumber)
						)
				.collect(Collectors.toList());
    }
    
    public void addLocation(String location){
    	Location l = new Location(location,totLocations);
    	totLocations++;
    	locations.put(l.getName(),l);
    }
//    public void addLocation(String name, double lat, double lon, double elevation){
//        
//    }

    public Location getLocation(String location){
        return locations.get(location);
    }

    public List<Location> getPath(){
        return locations.values().stream()
        		.sorted(Comparator.comparing(Location::getOrderNum))
        		.collect(Collectors.toList());
        
    }
    
    public void newDelegate(String name, String surname, String id){
        Delegate d = new Delegate(name, surname, id);
        delegates.put(d.getCf(), d);
    }

    public List<String> getDelegates(){
        return delegates.values().stream()
        		.sorted(Comparator.comparing(Delegate::getSurname)
        				.thenComparing(Delegate::getName))
        		.map(d -> d.toString())
        		.collect(Collectors.toList());
    }
    

    public void assignDelegate(String location, String delegate) throws TrailException {
       if(!locations.containsKey(location)) {throw new TrailException();}
       if(!delegates.containsKey(delegate)) {throw new TrailException();}
       Location l = locations.get(location);
       Delegate d = delegates.get(delegate);
       l.addDelegate(d);
       d.assignLocation(l);
    }
 
    public List<String> getDelegates(String location){
        return locations.get(location).getDelegatesAssigned().stream()
        		.sorted(Comparator.comparing(Delegate::getSurname))
        		.map(d -> d.toString())
        		.collect(Collectors.toList());
    }
    
    public long recordPassage(String delegate, String location, int bibNumber) throws TrailException {
        if(!delegates.containsKey(delegate)) {throw new TrailException();}
        if(!locations.containsKey(location)) {throw new TrailException();}
        if(!runners.containsKey(bibNumber)) {throw new TrailException();}
        Long time = System.currentTimeMillis();
        Location l = locations.get(location);
        Runner r = runners.get(bibNumber);
        if(!l.delegatesAssigned.contains(delegates.get(delegate))) {throw new TrailException();}

        r.addTime(l, time);
        l.addRunnersPassed(r, time);
    	return time;
    }
    
    public long getPassTime(String position, int bibNumber) throws TrailException {
    	if(!locations.containsKey(position)) {throw new TrailException();}
        if(!runners.containsKey(bibNumber)) {throw new TrailException();}
    	Runner r = runners.get(bibNumber);
        Location l = locations.get(position);
        if (!r.times.containsKey(l)) {
        	return -1;
    	} else {
    		return r.times.get(l);
    		}
    }
    
    public List<Runner> getRanking(String location){
    	List<Runner> risultato = null;
    	if(locations.containsKey(location)) {
    		Location l = locations.get(location);
    		risultato = l.runnersPassed.entrySet().stream()
        		.sorted(Comparator.comparing(Map.Entry<Runner, Long>::getValue))
        		.map(Map.Entry<Runner, Long>::getKey)
        		.collect(Collectors.toList());
    	}
		return risultato;
    }

    public List<Runner> getRanking(){
    	return runners.values().stream()
    					.sorted(Comparator.comparing(Runner::lastNum).reversed()
    										.thenComparing(Runner::lastTime))
    					.collect(Collectors.toList());
    }
}
