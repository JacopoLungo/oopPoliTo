package libraryMgmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Book {
	int nVol;
	ArrayList<String> authors = new ArrayList<>();
	String title;
	List<Volume> volumes = new ArrayList<>();
	public Book(int nVol, String[] authors, String title) {
		String [] aut = authors;
		Arrays.sort(aut);
		this.nVol = nVol;
		for(String a : aut) {
			this.authors.add(a);
		}
		
		this.title = title;
	}
	
	Volume getFirstAvailVol() {
	return	volumes.stream().filter(v -> v.available == true)
				.findFirst().get();
	}
	
	boolean areAllBookTaken() {
		for(Volume v : volumes) {
			if(v.available){
				return false;
				}
		}
		return true;
	}
 }
