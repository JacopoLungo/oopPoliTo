package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;


public class Assignment {
	
	String studentID;
	ExerciseChapter exerciseChapter;
	List<Double> scores = new ArrayList<>();
	

    public Assignment(String studentID, ExerciseChapter exerciseChapter) {
		this.studentID = studentID;
		this.exerciseChapter = exerciseChapter;
	}

	public String getID() {
        return studentID;
    }

    public ExerciseChapter getChapter() {
        return exerciseChapter;
    }

    public double addResponse(Question q, List<String> answers) {
    	long n = q.numAnswers();
    	long fp = 0;
    	long fn = 0;
    	
    	for (String s : answers) {
    		if(q.getIncorrectAnswers().contains(s)) {
    			fp++;
    		}
    	}
    	
    	for (String s : q.getCorrectAnswers()) {
    		if (!answers.contains(s)) {
    			fn++;
    		}
    	}
    	
    	double score = ((double) (n - fp - fn)) / ((double) n);
    	scores.add(score);
        return score;
    }
    
    public double totalScore() {
        return scores.stream().mapToDouble(x -> x).sum();
    }

}
