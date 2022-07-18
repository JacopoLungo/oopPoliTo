package trail;

import java.util.TreeMap;
import java.util.Map;
import java.util.SortedMap;

public class Runner {

    int bibNumber;
    String name;
    String surname;
    SortedMap<Location, Long> times = new TreeMap<>();
    
    
    public Runner(int bibNumber, String name, String surname) {
		super();
		this.bibNumber = bibNumber;
		this.name = name;
		this.surname = surname;
	}

	public int getBibNumber(){
        return bibNumber;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

	@Override
	public String toString() {
		return "Runner [bibNumber=" + bibNumber + ", name=" + name + ", surname=" + surname + "]";
	}
    
	void addTime(Location l, Long time) {
		times.put(l, time);
	}
	
	int lastNum() {
		return times.lastKey().getOrderNum();
	}
    
	Long lastTime() {
		return times.get(times.lastKey());
	}

}
