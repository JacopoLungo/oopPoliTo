package it.polito.oop.books;

import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

public class Topic implements Comparable<Topic> {
	String keyword;
	Map<String, Topic> subtopics = new TreeMap<>();
	
	public String getKeyword() {
        return keyword;
	}
	
	@Override
	public String toString() {
	    return keyword;
	}
	
	
	public boolean addSubTopic(Topic topic) {
		if(subtopics.containsKey(topic.getKeyword())) {
			return false;
		}
		else {
			subtopics.put(topic.getKeyword(), topic);
		}
        return true;
	}

	/*
	 * Returns a sorted list of subtopics. Topics in the list *MAY* be modified without
	 * affecting any of the Book topic.
	 */
	public List<Topic> getSubTopics() {
        return subtopics.values().stream()	
        		.collect(Collectors.toList());
	}



	@Override
	public int compareTo(Topic o) {
		return this.keyword.compareTo(o.keyword);
	}
}
