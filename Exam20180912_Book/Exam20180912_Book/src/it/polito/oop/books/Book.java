package it.polito.oop.books;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Book {
	
	Map <String, Topic> topics = new HashMap<>();
	List <Question> questions = new ArrayList<>();
	List <TheoryChapter> theoryChapters = new ArrayList<>();
	List <ExerciseChapter> exerciseChapters = new ArrayList<>();

	
    /**
	 * Creates a new topic, if it does not exist yet, or returns a reference to the
	 * corresponding topic.
	 * 
	 * @param keyword the unique keyword of the topic
	 * @return the {@link Topic} associated to the keyword
	 * @throws BookException
	 */
	public Topic getTopic(String keyword) throws BookException {
	    if (keyword.equals(null) || keyword.equals("")) {
	    	throw new BookException(); 
	    }
	    if(!topics.containsKey(keyword)) {
	    	Topic t = new Topic();
	    	topics.put(keyword, t);
	    	t.keyword = keyword;
	    }
		return topics.get(keyword);
	}

	public Question createQuestion(String question, Topic mainTopic) {
        Question q = new Question(question, mainTopic);
		questions.add(q);
		return q;
	}

	public TheoryChapter createTheoryChapter(String title, int numPages, String text) {
        TheoryChapter tc = new TheoryChapter(title, numPages, text);
        theoryChapters.add(tc);
        return tc;
	}

	public ExerciseChapter createExerciseChapter(String title, int numPages) {
        ExerciseChapter ec = new ExerciseChapter(title, numPages);
		exerciseChapters.add(ec);
        return ec;
	}

	public List<Topic> getAllTopics() {
        List<Topic> allTopic = new ArrayList<>();
		for (ExerciseChapter e : exerciseChapters) {
			for(Topic t : e.getTopics()) {
				allTopic.add(t);
			}
		}
		
		for (TheoryChapter t : theoryChapters) {
			for(Topic to : t.getTopics()) {
				allTopic.add(to);
			}
		}
		
        return allTopic.stream().distinct().sorted().collect(Collectors.toList());
	}

	public boolean checkTopics() {
		for (ExerciseChapter e : exerciseChapters) {
			for(Topic t : e.getTopics()) {
				boolean flag = false;
				
				for (TheoryChapter th : theoryChapters) {
					for(Topic to : th.getTopics()) {
						if(t.equals(to)) {
							flag = true;
						}
					}
				}
				if (flag == false) {return false;}
			}
		}
        return true;
	}

	public Assignment newAssignment(String ID, ExerciseChapter chapter) {
		Assignment a = new Assignment(ID, chapter);
		
        return a;
	}
	
    /**
     * builds a map having as key the number of answers and 
     * as values the list of questions having that number of answers.
     * @return
     */
    public Map<Long,List<Question>> questionOptions(){
        return questions.stream().collect(Collectors.toMap(Question::numAnswers,
        													q -> {List<Question> questions = new ArrayList<Question>();
        															questions.add(q);
        															return questions;},
        													(l1, l2) -> {
        														for(Question q : l2) {
        															l1.add(q);
        														}
        														return l1;
        						
        													} ));
    }
    
//	Ha la stessa funzione del metodo sopra ma è scritto in modo più sensato.
    
//	public Map<Long, List<Question>> questionOptions() {
//		return chapters.stream()
//		        .filter(c -> c instanceof ExerciseChapter)
//				.flatMap(c -> ((ExerciseChapter) c).questions.stream())
//				.collect(Collectors.groupingBy(q -> q.numAnswers(), Collectors.toList()));
}
