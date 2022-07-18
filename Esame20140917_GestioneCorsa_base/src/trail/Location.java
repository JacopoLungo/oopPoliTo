package trail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Location implements Comparable<Location> {

	String name;
	int orderNum;
	List<Delegate> delegatesAssigned = new ArrayList<>();
	Map<Runner, Long> runnersPassed = new HashMap<>();
	
    public Location(String name, int orderNum ) {
		this.name = name;
		this.orderNum = orderNum;
	}

	public String getName(){
        return name;
    }

    public int getOrderNum(){
        return orderNum;
    }
    
    void addDelegate(Delegate d){
    	delegatesAssigned.add(d);
    }

	public List<Delegate> getDelegatesAssigned() {
		return delegatesAssigned;
	}
	
	void addRunnersPassed(Runner r, Long time) {
		runnersPassed.put(r, time);
		}

	@Override
	public int compareTo(Location o) {
		return this.getOrderNum() - o.getOrderNum();
	}
    
    
}
