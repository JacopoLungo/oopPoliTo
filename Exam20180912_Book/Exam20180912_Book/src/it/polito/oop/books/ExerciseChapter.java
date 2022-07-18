package it.polito.oop.books;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class ExerciseChapter {
	String title;
	int numPages;
	List <Question> questions = new ArrayList<>();
	Set<Topic> topics = new HashSet<>();


	
	
    public ExerciseChapter(String title, int numPages) {
		this.title = title;
		this.numPages = numPages;
	}


	public List<Topic> getTopics() {
        return topics.stream().sorted().collect(Collectors.toList());
	};
	

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
    

	public void addQuestion(Question question) {
		questions.add(question);
		topics.add(question.getMainTopic());
	}	
}
