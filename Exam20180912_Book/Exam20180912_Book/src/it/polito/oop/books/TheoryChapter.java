package it.polito.oop.books;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class TheoryChapter {
	String title;
	int numPages;
	String text;
	Set<Topic> topics = new HashSet<>();
	
    public TheoryChapter(String title, int numPages, String text) {
		this.title = title;
		this.numPages = numPages;
		this.text = text;
	}

	public String getText() {
		return text;
	}

    public void setText(String newText) {
    	this.text = newText;
    }


	public List<Topic> getTopics() {
        return topics.stream().sorted().collect(Collectors.toList());
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
    	this.title = newTitle;
    }

    public int getNumPages() {
        return numPages;
    }
    
    public void setNumPages(int newPages) {
    	this.numPages = newPages;
    }
    

    
    public void addTopic(Topic topic) {
    	if(!topics.contains(topic)) {
			topics.add(topic);
			for(Topic t: topic.getSubTopics())
				addTopic(t);
		} 
    }
    
    
}
