package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Question {
	String qstText;
	Topic qstTopic;
	List<Answer> answers = new ArrayList<>();
	
	
	public Question(String qstText, Topic qstTopic) {
		this.qstText = qstText;
		this.qstTopic = qstTopic;
	}

	public String getQuestion() {
		return qstText;
	}
	
	public Topic getMainTopic() {
		return qstTopic;
	}

	public void addAnswer(String answer, boolean correct) {
		Answer a = new Answer(answer, correct);
		answers.add(a);
	}
	
    @Override
    public String toString() {
        return this.getQuestion()+ " (" +this.getMainTopic().toString() + ")";
    }

	public long numAnswers() {
	    return answers.size();
	}

	public Set<String> getCorrectAnswers() {
		return answers.stream().filter(a -> a.isCorrect)
				.map(a -> a.text)
				.collect(Collectors.toSet());
	}

	public Set<String> getIncorrectAnswers() {
		return answers.stream().filter(a -> !(a.isCorrect))
				.map(a -> a.text)
				.collect(Collectors.toSet());	}
}
